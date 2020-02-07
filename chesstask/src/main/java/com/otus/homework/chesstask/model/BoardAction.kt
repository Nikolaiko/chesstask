package com.otus.homework.chesstask.model

import com.example.core.model.task.FigurePosition

data class BoardAction(
    val figure:ChessFigureOnBoard,
    val startPosition:FigurePosition,
    val endPosition: FigurePosition,
    val removedFigure: ChessFigureOnBoard?,
    val promotedFigure: ChessFigureOnBoard? = null
) {
    fun reverse() {

    }
}