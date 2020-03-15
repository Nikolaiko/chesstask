package com.otus.homework.taskslist.presenters

import com.example.core.model.enums.ChessTaskDifficulty
import com.otus.homework.taskslist.views.TasksView

interface TasksPresenter {
    fun attachView(view: TasksView)
    fun detachView()
    fun getTaskByDifficulty(difficulty: ChessTaskDifficulty)
}
