package com.otus.homework.chesstask.views

import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.board.BoardAction
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import io.reactivex.Observable

interface ChessTaskView {
    val selectedFigureId: Observable<String>
    val selectedCell: Observable<FigurePosition>

    fun updateChessBoardSelection(selectedCells: List<FigurePosition>)
    fun updateChessBoardPosition(position: List<ChessFigureOnBoard>)
    fun applyAction(action: BoardAction)
}