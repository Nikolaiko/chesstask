package com.otus.homework.taskslist

import android.content.Context
import com.example.core_api.mediator.TasksListMediator
import javax.inject.Inject

class TasksListMediatorImpl @Inject constructor() : TasksListMediator {
    override fun createTasksListActivity(context: Context) {
        TasksListActivity.startOnTasksListActivity(context)
    }
}