package com.example.core_api.model.task

import com.example.core_api.model.enums.ChessFigureColor
import com.example.core_api.model.enums.ChessFigureType

data class ChessFigure (val figureType: ChessFigureType,
                        val color: ChessFigureColor,
                        val position: FigurePosition)