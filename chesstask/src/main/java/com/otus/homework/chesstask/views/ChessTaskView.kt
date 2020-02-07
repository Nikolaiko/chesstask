package com.otus.homework.chesstask.views

import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.ChessFigureOnBoard
import io.reactivex.Observable

interface ChessTaskView {
    val selectedFigureId: Observable<String>

    fun updateChessBoardSelection(selectedCells: List<FigurePosition>)
    fun updateChessBoardPosition(position: List<ChessFigureOnBoard>)
}