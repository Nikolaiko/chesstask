package com.example.core.model.task

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType

data class ChessFigure (
    val figureType: ChessFigureType,
    val color: ChessFigureColor,
    val position: FigurePosition
)