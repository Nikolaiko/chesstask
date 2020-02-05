package com.otus.homework.taskslist.presenters

import com.otus.homework.taskslist.views.TasksView

interface TasksPresenter {
    fun attachView(view: TasksView)
    fun detachView()
}