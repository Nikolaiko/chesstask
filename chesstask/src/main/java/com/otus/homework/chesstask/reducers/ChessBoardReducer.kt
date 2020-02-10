package com.otus.homework.chesstask.reducers

import com.example.core.model.task.ChessTask
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.ChessTaskMessageId
import com.otus.homework.chesstask.model.ChessTaskNews
import com.otus.homework.chesstask.model.ChessTaskState
import com.otus.homework.chesstask.model.board.BoardAction
import com.otus.homework.chesstask.model.board.ChessBoardState
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ChessBoardReducer @Inject constructor() : BoardReducer {
    private var chessTask: ChessTask? = null

    private var currentTurn: Int = 0
    private var selectedFigureId: String? = null
    private val availableForMoveCells: MutableList<FigurePosition> = mutableListOf()

    private val boardState = ChessBoardState()
    private var chessTaskState = ChessTaskState.PREPARING

    private val _updateNews: PublishSubject<ChessTaskNews> =
        PublishSubject.create()
    override val updateNews: Observable<ChessTaskNews>
        get() = _updateNews

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
        currentTurn = 0

        for (currentFigure in task.startingPositions) {
            boardState.addFigureToBoard(ChessFigureOnBoard.convert(currentFigure))
        }
        _updateBoardPosition.onNext(boardState.getFigures())
        chessTaskState = ChessTaskState.IN_PROGRESS
    }

    override fun selectFigureById(figureId: String) {
        val selectedFigure = boardState.getFigureById(figureId)
        if (selectedFigure != null) {
            if (selectedFigure.color == chessTask?.activeColor && figureId != selectedFigureId) {
                updateSelectedFigure(figureId)
            } else {
                if (isCellAvailable(selectedFigure.position)) {
                    makeMoveAndCheck(selectedFigure.position, selectedFigure)
                }
            }
        } else {
            _updateNews.onNext(ChessTaskNews(ChessTaskMessageId.CANt_FIND_FIGURE_BY_ID))
        }
    }

    override fun selectCellAt(position: FigurePosition) {
        if (isCellAvailable(position)) {
            makeMoveAndCheck(position)
        }
    }

    private fun updateSelectedFigure(id: String) {
        selectedFigureId = id
        availableForMoveCells.clear()
        availableForMoveCells.addAll(boardState.getAvailableCellsForFigure(selectedFigureId!!))
        _updateBoardCellSelection.onNext(availableForMoveCells)
    }

    private fun makeMoveAndCheck(destination:FigurePosition,
                                 attackedFigure: ChessFigureOnBoard? = null) {
        makeMove(destination, attackedFigure)
        if (isMoveCorrect(destination)) {
            println("Correct move!!!")
            //blackTurn
            currentTurn += 1
            checkTaskFinish()
        } else {
            println("Wrong move!!!")
            //message
        }
    }

    private fun makeMove(destination:FigurePosition,
                         attackedFigure: ChessFigureOnBoard? = null) {
        val activeFigure = boardState.getFigureById(selectedFigureId!!)!!
        val action = BoardAction(
            activeFigure,
            activeFigure.position,
            destination,
            attackedFigure
        )
        boardState.applyAction(action)
        _applyBoardAction.onNext(action)
    }

    private fun isMoveCorrect(position: FigurePosition): Boolean {
        val currentPgnMove = chessTask!!.pgnMoves[currentTurn].whiteMove
        return currentPgnMove.destination != position
    }

    private fun checkTaskFinish() {
        if (currentTurn == chessTask!!.pgnMoves.size) {
            chessTaskState = ChessTaskState.WON
            //message
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

    private fun makeAIMove() {
        val blackTurn = chessTask!!.pgnMoves[currentTurn].blackMove
        if (blackTurn != null) {

            BoardAction(

            )
        }
    }
}