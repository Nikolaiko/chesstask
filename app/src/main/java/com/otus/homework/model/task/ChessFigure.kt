package com.otus.homework.model.task

import com.otus.homework.model.enums.ChessFigureColor
import com.otus.homework.model.enums.ChessFigureType

data class ChessFigure (val figureType:ChessFigureType,
                        val color:ChessFigureColor,
                        val position: FigurePosition)