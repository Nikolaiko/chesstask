package com.otus.homework.chesstask.model

data class ChessBoard (
    val figures:MutableList<ChessFigureOnBoard> = mutableListOf(),
    val history:MutableList<ChessMove> = mutableListOf()
)