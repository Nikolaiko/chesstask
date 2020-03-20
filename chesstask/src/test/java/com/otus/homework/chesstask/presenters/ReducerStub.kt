package com.otus.homework.chesstask.presenters

import com.example.core.model.task.ChessTask
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.ChessTaskNews
import com.otus.homework.chesstask.model.board.BoardAction
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import com.otus.homework.chesstask.reducers.BoardReducer
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ReducerStub() : BoardReducer {
    override val updateBoardPosition: Observable<List<ChessFigureOnBoard>> = PublishSubject.create()
    override val updateBoardCellSelection: Observable<List<FigurePosition>> = PublishSubject.create()
    override val updateNews: Observable<ChessTaskNews> = PublishSubject.create()
    override val applyBoardAction: Observable<BoardAction> = PublishSubject.create()

    private var initChessTaskCalled: Boolean = false
    private var initChessTaskParameter: ChessTask? = null

    fun initChessTaskCalledWith(task: ChessTask): Boolean {
        return initChessTaskCalled && task == initChessTaskParameter
    }

    override fun initChessTask(task: ChessTask) {
        initChessTaskCalled = true
        initChessTaskParameter = task
    }

    override fun selectFigureById(figureId: String) {}

    override fun selectCellAt(position: FigurePosition) {}

    override fun openSolution() {}

    override fun undoLastMove() {}

    override fun restartTask() {}

    override fun exitTask() {}
}