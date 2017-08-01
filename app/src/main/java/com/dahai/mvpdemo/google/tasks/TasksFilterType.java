package com.dahai.mvpdemo.google.tasks;

/**
 * Created by 张海洋 on 2017/7/6.
 */

public enum TasksFilterType {
    /**
     *  Do not filter tasks.
     */
    ALL_TASKS,
    /**
     * Filters only the active(not completed yet) tasks.
     */
    ACTIVE_TASKS,
    /**
     *  Filter only the completed tasks.
     */
    COMPLETED_TASKS
}
