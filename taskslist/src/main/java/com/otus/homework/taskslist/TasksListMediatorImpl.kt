package com.otus.homework.taskslist

import android.content.Context
import com.example.core.mediator.TasksListMediator
import javax.inject.Inject

class TasksListMediatorImpl @Inject constructor() : TasksListMediator {
    override fun createTasksListActivity(context: Context) {
        TasksListActivity.startOnTasksListActivity(context)
    }
}