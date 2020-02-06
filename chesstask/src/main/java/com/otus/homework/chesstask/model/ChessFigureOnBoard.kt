package com.otus.homework.chesstask.model

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.task.FigurePosition

data class ChessFigureOnBoard(
    val id:String,
    val color:ChessFigureColor,
    val position:FigurePosition
)