package com.dahai.mvpdemo.google.data.source;

import android.support.annotation.NonNull;

import com.dahai.mvpdemo.google.data.Task;

import java.util.List;

/**
 *  Main entry point for accessing tasks data;
 *  <p>
 *      For simplicity,only getTasks() and getTask() have callbacks.Consider adding callbacks to other
 *      methods to inform the user of network/database errors or successful oprations.
 *      For example,when a new task is create,it's synchronously stored in cache but ususlly every
 *      operation on database or network should be executed in a different thread.
 *  </p>
 * Created by 张海洋 on 2017/7/9.
 */

public interface TasksDataSource {

    interface LoadTasksCallback {
        void onTasksLoaded(List<Task> tasks);
        void onDataNotAvailable();
    }

    interface GetTaskCallback {
        void onTaskLoaded(Task task);
        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallback callback);
    void getTask(@NonNull String taskId,@NonNull GetTaskCallback callback);
    void saveTask(@NonNull Task task);
    void completeTask(@NonNull Task task);
    void completeTask(@NonNull String taskId);
    void activateTask(@NonNull Task task);
    void activateTask(@NonNull String taskId);
    void clearCompletedTasks();
    void refreshTasks();
    void deleteAllTasks();
    void deleteTask(@NonNull String taskId);
}
