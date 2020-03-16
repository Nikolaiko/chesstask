package com.otus.homework.chesstask.presenters

import com.example.core.model.task.ChessTask
import com.otus.homework.chesstask.views.ChessTaskView

interface BoardPresenter {
    fun openSolution()
    fun setBoardTask(task: ChessTask)
    fun attachView(view: ChessTaskView)
    fun detachView()
}
