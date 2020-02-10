package com.otus.homework.storage

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.ChessFigure
import com.example.core.model.task.FigurePosition

fun getStartingColor(fenString:String?): ChessFigureColor {
    return if (fenString?.isNotBlank() == true) {
        val secondPart = fenString.split(' ')[1]
        ChessFigureColor.valueOf(secondPart[0].toString())
    } else {
        ChessFigureColor.w
    }
}

fun getStartingPositions(fenString:String?):List<ChessFigure> {
    val startingPositions = mutableListOf<ChessFigure>()
    if (fenString?.isNotBlank() == true) {
        val firstPart = fenString.split(' ')[0]
        val rows = firstPart.split('/')

        var rowIndex = 0
        for (currentRow in rows) {
            var currentColumn:Int = 0
            for (currentChar in currentRow) {
                if (currentChar.isDigit()) {
                    val skipCount = currentChar.toString().toInt()
                    currentColumn += skipCount
                } else {
                    val figureColor = getColorFromFigureChar(currentChar)
                    val lowerCased = currentChar.toLowerCase()
                    val figureType = getFigureTypeFromChar(lowerCased)
                    val figurePosition = FigurePosition(rowIndex, currentColumn)

                    startingPositions.add(ChessFigure(figureType, figureColor, figurePosition))
                    currentColumn += 1
                }
            }
            rowIndex += 1
        }
    }
    return startingPositions
}

fun getColorFromFigureChar(figureChar:Char): ChessFigureColor = when(figureChar.isUpperCase()) {
    true -> ChessFigureColor.w
    false -> ChessFigureColor.b
}

fun getFigureTypeFromChar(figureChar:Char): ChessFigureType = ChessFigureType.values().first { it.figureName == figureChar.toString() }