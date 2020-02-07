package com.otus.homework.chesstask.reducers

import com.example.core.model.task.ChessTask
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.BoardAction
import com.otus.homework.chesstask.model.ChessBoardState
import com.otus.homework.chesstask.model.ChessFigureOnBoard
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ChessBoardReducer @Inject constructor() : BoardReducer {
    private var chessTask: ChessTask? = null

    private var selectedFigureId: String? = null
    private val availableForMoveCells: MutableList<FigurePosition> = mutableListOf()
    private val boardState = ChessBoardState()

    private val _applyBoardAction: PublishSubject<BoardAction> =
        PublishSubject.create()
    override val applyBoardAction: Observable<BoardAction>
        get() = _applyBoardAction

    private val _updateBoardPosition: PublishSubject<List<ChessFigureOnBoard>> =
        PublishSubject.create()
    override val updateBoardPosition: Observable<List<ChessFigureOnBoard>>
        get() = _updateBoardPosition

    private val _updateBoardCellSelection: PublishSubject<List<FigurePosition>> =
        PublishSubject.create()
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
        val selectedFigure = boardState.getFigureById(figureId)
        if (selectedFigure != null) {

            if (selectedFigure.color == chessTask?.activeColor) {
                selectedFigureId = figureId
                availableForMoveCells.clear()
                availableForMoveCells.addAll(boardState.getAvailableCellsForFigure(figureId))
                _updateBoardCellSelection.onNext(availableForMoveCells)
            } else {
                if (isCellAvailable(selectedFigure.position)) {
                    val activeFigure = boardState.getFigureById(selectedFigureId!!)!!
                    val action = BoardAction(
                        activeFigure,
                        activeFigure.position,
                        selectedFigure.position,
                        selectedFigure
                    )
                    boardState.applyAction(action)
                    _applyBoardAction.onNext(action)
                }
            }

        } else {
            //error news
        }
    }

    private fun isCellAvailable(position: FigurePosition): Boolean {
        var available = false
        for (cell in availableForMoveCells) {
            if (cell == position) {
                available = true
                break
            }
        }
        return available
    }
}