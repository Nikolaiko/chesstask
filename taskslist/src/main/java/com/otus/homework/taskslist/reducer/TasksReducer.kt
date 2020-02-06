package com.otus.homework.taskslist.reducer

import com.otus.homework.taskslist.model.TasksListNews
import com.otus.homework.taskslist.model.TasksListState
import com.otus.homework.taskslist.model.enums.TasksListScreens
import io.reactivex.Observable

interface TasksReducer {
    val updateState:Observable<TasksListState>
    val updateNews:Observable<TasksListNews>
    val updateDestination:Observable<TasksListScreens>

    fun refreshTasks()
    fun getTaskById(id:String)

    fun clearDisposables()
}