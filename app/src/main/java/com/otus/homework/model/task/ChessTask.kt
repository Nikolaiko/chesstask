package com.otus.homework.model.task

import com.otus.homework.model.enums.ChessFigureColor

data class ChessTask(
    val id:String,
    val startingPositions:List<ChessFigure>,
    val activeColor:ChessFigureColor
)