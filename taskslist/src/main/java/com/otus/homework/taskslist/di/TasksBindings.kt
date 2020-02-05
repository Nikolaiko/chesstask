package com.otus.homework.taskslist.di

import com.otus.homework.taskslist.presenters.ChessTasksPresenter
import com.otus.homework.taskslist.presenters.TasksPresenter
import com.otus.homework.taskslist.reducer.TasksListReducer
import com.otus.homework.taskslist.reducer.TasksReducer
import com.otus.homework.taskslist.views.TasksView
import dagger.Binds
import dagger.Module

@Module
interface TasksBindings {
    @Binds
    fun bindTasksPresenter(presenter:ChessTasksPresenter):TasksPresenter

    @Binds
    fun bindTasksListReducer(reducer:TasksListReducer):TasksReducer
}