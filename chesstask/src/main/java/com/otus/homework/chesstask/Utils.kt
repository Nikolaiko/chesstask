package com.otus.homework.chesstask

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.task.ChessTask
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove

fun getSolutionFromPgn(task: ChessTask): String {
    var stringSolution = ""
    for (currentMove in task.pgnMoves) {
        if (task.activeColor == ChessFigureColor.b) {
            val currentString = pgnToString(currentMove.blackMove)
        }
    }
    return stringSolution
}

private fun pgnToString(pgnMove: PgnMove?): String? {
    var result: String? = null
    if (pgnMove != null) {
        val startPosition = convertPositionToString(pgnMove.start)
        val destinationPosition = convertPositionToString(pgnMove.destination)
        if (startPosition != null) {
            result = "${pgnMove.figureType.figureName} - $startPosition:$destinationPosition"
        } else {
            result = "${pgnMove.figureType.figureName} - $destinationPosition"
        }
    }
    return result
}

private fun convertPositionToString(position: FigurePosition?): String? {
    var positionString: String? = null
    if (position != null) {
        positionString = "${getColumnString(position.column)}${getRowString(position.row)}"
    }
    return positionString
}

private fun getColumnString(column: Int): String = when(column) {
    0 -> "A"
    1 -> "B"
    2 -> "C"
    3 -> "D"
    4 -> "E"
    5 -> "F"
    6 -> "G"
    7 -> "H"
    else -> "unknown"
}

private fun getRowString(row: Int): String = when(row) {
    0 -> "1"
    1 -> "2"
    2 -> "3"
    3 -> "4"
    4 -> "5"
    5 -> "6"
    6 -> "7"
    7 -> "8"
    else -> "unknown"
}