package com.dahai.mvpdemo.google.data.source;

import android.support.annotation.NonNull;

import com.dahai.mvpdemo.google.data.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * concrete implementtation to load tasks from the data sources into a cache.
 * For simplicity,this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server,by using the remote data source only if the local database doesn't
 * exist or is empty
 * Created by 张海洋 on 2017/7/9.
 */

public class TasksRepository implements TasksDataSource{
    private static TasksRepository INSTANCE = null;
    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksDataSource mTasksLocalDataSource;
    /**
     *  This variable has package local visibility so it can be accessed from tests
     */
    Map<String,Task> mCachedTasks;
    /**
     *  Marks the cache as invalid,to force an update the next time data is requested.This variable
     *  has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;
    // prevent direct instantiation.
    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,@NonNull TasksDataSource tasksLocalDataSource) {
        mTasksLocalDataSource = checkNotNull(tasksRemoteDataSource);
        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource);
    }

    /**
     *  Returns the single instance of this class,creating it if necessary.
     * @param tasksRemoteDataSource tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource the device storage data source
     * @return the {@link TasksRepository} instance
     */
    public static TasksRepository getInstance(TasksDataSource tasksRemoteDataSource,TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource,tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    /**
     *  Gets tasks from cache,local data source(SQLite) or remote data source,whichever is available first.
     *  Note:{@link LoadTasksCallback#onDataNotAvailable()}is fired if fired if all data sources fail to get the data.
     * @param callback
     */
    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        checkNotNull(callback);

        // respond immediately with cache if available and not dirty
        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onTasksLoaded(new ArrayList<Task>(mCachedTasks.values()));
            return;
        }
        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback);
        } else {
            mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
                @Override
                public void onTasksLoaded(List<Task> tasks) {
                    refreshCache(tasks);
                    callback.onTasksLoaded(new ArrayList<Task>(mCachedTasks.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getTasksFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void getTasksFromRemoteDataSource(final LoadTasksCallback callback) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);
                refreshLocalDataSource(tasks);
                callback.onTasksLoaded(new ArrayList<Task>(mCachedTasks.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Task> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    private void refreshCache(List<Task> tasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        for (Task task:tasks) {
            mCachedTasks.put(task.getId(),task);
        }
        mCacheIsDirty = false;
    }

    /**
     *  Gets tasks from local data source(sqlite) unless the table is new or empty.In that case it
     *  uses the network data source.This is done to simplify the sample.
     * @param taskId
     * @param callback
     */
    @Override
    public void getTask(@NonNull final String taskId, @NonNull final GetTaskCallback callback) {
        checkNotNull(taskId);
        checkNotNull(callback);

        Task cachedTask = getTaskWithId(taskId);

        // Respond immediately with cache if available
        if (cachedTask != null) {
            callback.onTaskLoaded(cachedTask);
            return;
        }
        // Load from server/persisted if needed.
        // Is the task in the local data source? If not,query the network.
        mTasksLocalDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedTasks == null) {
                    mCachedTasks = new LinkedHashMap<String, Task>();
                }
                mCachedTasks.put(task.getId(),task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                mTasksRemoteDataSource.getTask(taskId, new GetTaskCallback() {
                    @Override
                    public void onTaskLoaded(Task task) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedTasks == null) {
                            mCachedTasks = new LinkedHashMap<String, Task>();
                        }
                        mCachedTasks.put(task.getId(),task);
                        callback.onTaskLoaded(task);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);
        // Do in memory cache update to keep the app UI up to date
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(),task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task completedTask = new Task(task.getTitle(),task.getDescription(),task.getId(),true);
        // Do in memory cache update to keep the app UI up to date
        if ((mCachedTasks == null)) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(),completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        checkNotNull(taskId);
        completeTask(getTaskWithId(taskId));
    }

    private Task getTaskWithId(@NonNull String taskId) {
        checkNotNull(taskId);
        if (mCachedTasks == null || mCachedTasks.isEmpty()) {
            return null;
        } else {
            return mCachedTasks.get(taskId);
        }
    }

    @Override
    public void activateTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.activateTask(task);
        mTasksLocalDataSource.activateTask(task);

        Task activeTask = new Task(task.getTitle(),task.getDescription(),task.getId());
        // Do in memory cache update to keep the app UI to data
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(),activeTask);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        checkNotNull(taskId);
        activateTask(getTaskWithId(taskId));
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRemoteDataSource.clearCompletedTasks();
        mTasksLocalDataSource.clearCompletedTasks();

        // Do in memory cache update to keep the app UI up to data
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String,Task>> it = mCachedTasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksRemoteDataSource.deleteTask(taskId);
        mTasksLocalDataSource.deleteTask(taskId);

        mCachedTasks.remove(taskId);
    }
}
