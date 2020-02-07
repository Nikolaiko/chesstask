package com.otus.homework.chesstask.reducers

import com.example.core.model.task.ChessTask
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.ChessBoardState
import com.otus.homework.chesstask.model.ChessFigureOnBoard
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ChessBoardReducer @Inject constructor() : BoardReducer {

    private var selectedFigureId:String? = null
    private var chessTask: ChessTask? = null
    private val boardState = ChessBoardState()

    private val _updateBoardPosition: PublishSubject<List<ChessFigureOnBoard>> = PublishSubject.create()
    override val updateBoardPosition: Observable<List<ChessFigureOnBoard>>
        get() = _updateBoardPosition

    private val _updateBoardCellSelection: PublishSubject<List<FigurePosition>> = PublishSubject.create()
    override val updateBoardCellSelection: Observable<List<FigurePosition>>
        get() = _updateBoardCellSelection

    override fun initChessTask(task: ChessTask) {
        chessTask = task
        for (currentFigure in task.startingPositions) {
            boardState.addFigureToBoard(ChessFigureOnBoard.convert(currentFigure))
        }
        _updateBoardPosition.onNext(boardState.getFigures())
    }

    override fun selectFigureById(figureId: String) {
        selectedFigureId = figureId
        _updateBoardCellSelection.onNext(boardState.getAvailableCellsForFigure(figureId))
    }
}