package com.otus.homework.taskslist.views

import com.example.core.model.task.ChessTask
import com.example.core.model.task.ChessTaskShortInfo
import com.otus.homework.taskslist.model.TasksListNews
import com.otus.homework.taskslist.model.enums.TasksListScreens
import io.reactivex.Observable

interface TasksView {
    val selectedTask: Observable<ChessTaskShortInfo>

    fun displayMessage(newsMessage: TasksListNews)
    fun navigateTo(destination: TasksListScreens, task:ChessTask?)
    fun setLoadingVisibility(visible:Boolean)
    fun updateTasksList(list:List<ChessTaskShortInfo>)
}
