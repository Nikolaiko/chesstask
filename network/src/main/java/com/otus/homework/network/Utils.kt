package com.otus.homework.network

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType

fun getColorFromFigureChar(figureChar:Char): ChessFigureColor = when(figureChar.isUpperCase()) {
    true -> ChessFigureColor.w
    false -> ChessFigureColor.b
}

fun getFigureTypeFromChar(figureChar:Char): ChessFigureType = ChessFigureType.values().first { it.figureName == figureChar.toString() }