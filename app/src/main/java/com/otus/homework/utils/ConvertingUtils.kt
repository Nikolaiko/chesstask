package com.otus.homework.utils

import com.otus.homework.model.enums.ChessFigureColor
import com.otus.homework.model.enums.ChessFigureType

fun getColorFromFigureChar(figureChar:Char): ChessFigureColor = when(figureChar.isUpperCase()) {
    true -> ChessFigureColor.w
    false -> ChessFigureColor.b
}

fun getFigureTypeFromChar(figureChar:Char): ChessFigureType = ChessFigureType.values().first { it.figureName == figureChar.toString() }