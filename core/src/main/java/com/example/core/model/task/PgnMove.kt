package com.example.core.model.task

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType

data class PgnMove (
    val figureType: ChessFigureType,
    val figureColor: ChessFigureColor,
    val destination: FigurePosition,
    val start:FigurePosition?,
    val takeOppositeFigure: Boolean = false,
    val promotedFigure: ChessFigureType? = null
)