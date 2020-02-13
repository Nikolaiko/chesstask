package com.example.core.model.task

import com.example.core.model.enums.ChessFigureColor

data class ChessTask(
    val id: String,
    val startingPositions: List<ChessFigure>,
    val activeColor: ChessFigureColor,
    val pgnMoves: List<PgnMovePair>
)
