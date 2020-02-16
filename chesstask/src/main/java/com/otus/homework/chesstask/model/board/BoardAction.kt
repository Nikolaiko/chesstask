package com.otus.homework.chesstask.model.board

import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard

data class BoardAction(
    val figure: ChessFigureOnBoard,
    val startPosition:FigurePosition,
    val endPosition: FigurePosition,
    val removedFigure: ChessFigureOnBoard? = null,
    val promotedFigure: ChessFigureOnBoard? = null,
    val addedFigure: ChessFigureOnBoard? = null
) {
    fun reverse(): BoardAction = BoardAction(
        figure,
        endPosition,
        startPosition,
        null,
        figure.copy(figureType = promotedFigure?.figureType ?: figure.figureType),
        removedFigure
    )
}
