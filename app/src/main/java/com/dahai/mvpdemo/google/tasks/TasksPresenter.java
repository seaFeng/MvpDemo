package com.dahai.mvpdemo.google.tasks;

import android.support.annotation.NonNull;

import com.dahai.mvpdemo.google.data.Task;
import com.dahai.mvpdemo.google.data.source.TasksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 张海洋 on 2017/7/9.
 */

public class TasksPresenter implements TasksContract.Presenter {
    private final TasksRepository mTasksRepository;
    private final TasksContract.View mTasksView;
    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;
    private boolean mFirstLoad = true;

    public TasksPresenter(@NonNull TasksRepository tasksRepository,@NonNull TasksContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository,"tasksRepository cannot be null!");
        mTasksView = checkNotNull(tasksView,"tasksView cannot be null!");

        mTasksView.setPresenter(this);
    }

    public void start() {
        loadTasks(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // if a task was successfully added,show snackbar
        //if ()
    }

    @Override
    public void loadTasks(boolean forceUpdate) {

    }

    @Override
    public void addNewTask() {

    }

    @Override
    public void openTaskDetails(@NonNull Task requestedTask) {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void setFiltering(TasksFilterType requestType) {

    }

    @Override
    public TasksFilterType getFiletering() {
        return null;
    }
}
