package com.otus.homework.app.shadow

import com.example.core.model.task.ChessTask
import com.otus.homework.chesstask.presenters.BoardPresenter
import com.otus.homework.chesstask.presenters.ChessBoardPresenter
import com.otus.homework.chesstask.views.ChessTaskView
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(ChessBoardPresenter::class)
class ChessTaskPresenterShadow : BoardPresenter {
    companion object {
        var actualTask: ChessTask? = null
    }

    @Implementation
    override fun setBoardTask(task: ChessTask) {
        actualTask = task
    }

    @Implementation
    override fun attachView(view: ChessTaskView) {

    }

    @Implementation
    override fun detachView() {

    }


}