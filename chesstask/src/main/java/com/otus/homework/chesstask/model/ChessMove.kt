package com.otus.homework.chesstask.model

import com.example.core.model.task.FigurePosition

data class ChessMove(
    val figure:ChessFigureOnBoard,
    val startPosition:FigurePosition,
    val endPosition: FigurePosition
) {
    fun reverse() {

    }
}