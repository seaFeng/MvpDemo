package com.dahai.mvpdemo.google.tasks;

import android.support.annotation.NonNull;

import com.dahai.mvpdemo.google.BasePresenter;
import com.dahai.mvpdemo.google.BaseView;
import com.dahai.mvpdemo.google.data.Task;

import java.util.List;

/**
 * Created by 张海洋 on 2017/7/4.
 */

public class TasksContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showTasks(List<Task> tasks);
        void showAddTask();
        void showTaskDetailsUi(String taskId);
        void showTaskMarkedComplete();
        void showTaskMarkedActive();
        void showCompletedTasksCleared();
        void showLoadingTaskError();
        void showNoTasks();
        void showActiveFilterLabel();
        void showCompletedFilterLabel();
        void showAllFilterLabel();
        void showNoActiveTasks();
        void showNoCompletedTasks();
        void showSuccessfullySavedMessage();
        boolean isActive();
        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode,int resultCode);
        void loadTasks(boolean forceUpdate);
        void addNewTask();
        void openTaskDetails(@NonNull Task requestedTask);
        void completeTask(@NonNull Task completedTask);
        void activateTask(@NonNull Task activeTask);
        void clearCompletedTasks();
        void setFiltering(TasksFilterType requestType);
        TasksFilterType getFiletering();
    }
}
