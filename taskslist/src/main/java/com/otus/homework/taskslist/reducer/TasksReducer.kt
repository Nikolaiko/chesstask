package com.otus.homework.taskslist.reducer

import com.example.core.model.enums.ChessTaskDifficulty
import com.otus.homework.taskslist.model.TasksListNews
import com.otus.homework.taskslist.model.TasksListState
import com.otus.homework.taskslist.model.enums.TasksListScreens
import io.reactivex.Observable

interface TasksReducer {
    val updateState: Observable<TasksListState>
    val updateNews: Observable<TasksListNews>
    val updateDestination: Observable<TasksListScreens>

    fun logout()
    fun refreshTasks()
    fun getTaskById(id: String)
    fun getTaskByDifficulty(difficulty: ChessTaskDifficulty)

    fun clearDisposables()
}
