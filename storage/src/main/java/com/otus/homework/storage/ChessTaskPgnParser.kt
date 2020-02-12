package com.otus.homework.storage

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove
import com.example.core.model.task.PgnMovePair

fun parsePgnString(pgn: String): List<PgnMovePair> {
    val pgnTurns: MutableList<PgnMovePair> = mutableListOf()
    val chessMoveSplitRegex = Regex("\\d{1,3}[.]")
    val splittedPgn = pgn.split(chessMoveSplitRegex).filter { it.isNotEmpty() }

    val trimmedPgn = mutableListOf<String>()
    splittedPgn.forEach { trimmedPgn.add(it.trim()) }

    trimmedPgn.forEach {
        pgnTurns.add(parseSingleTurn(it))
    }
    return pgnTurns.toList()
}

private fun parseSingleTurn(turn: String): PgnMovePair {
    val splittedMoves = turn.split(" ")
    val whiteMoveString = splittedMoves[0].trim('=', '+', '-', '/', '!', '?', '#', ' ')

    var whiteMove: PgnMove? = null
    if (whiteMoveString != "..") {
        whiteMove = parsePgnMove(whiteMoveString, ChessFigureColor.w)
    }

    var blackMove: PgnMove? = null
    if (splittedMoves.size > 1) {
        val blackMoveString = splittedMoves[1].trim('=', '+', '-', '/', '!', '?', '#', ' ')
        blackMove = parsePgnMove(blackMoveString, ChessFigureColor.b)
    }
    return PgnMovePair(whiteMove, blackMove)
}

private fun parsePgnMove(move: String, color: ChessFigureColor): PgnMove {
    var figureType = ChessFigureType.pawn
    var attackEnemyFigure = false

    var withoutFigureType = move
    if (move[0].isUpperCase()) {
        figureType = parsePgnLetterToFigure(move[0])
        withoutFigureType  = withoutFigureType.substring(1)
    }

    var destinationPart:String? = null
    var startPart:String? = null

    if (withoutFigureType.contains('x')) {
        attackEnemyFigure = true

        val turnParts = withoutFigureType.split('x')
        startPart = turnParts[0]
        destinationPart = turnParts[1]
    } else {
        if (withoutFigureType.length > 2) {
            destinationPart = withoutFigureType.substring(withoutFigureType.length - 2)
            startPart = withoutFigureType.substring(0, withoutFigureType.length - 2)
        } else {
            destinationPart = withoutFigureType
        }
    }

    val destPosition = parsePgnStringToPosition(destinationPart)
    var startPosition:FigurePosition? = null
    if (startPart?.isNotBlank() == true) {
        startPosition = parsePgnStringToPosition(startPart)
    }

    return PgnMove(
        figureType,
        color,
        destPosition,
        startPosition,
        attackEnemyFigure
    )
}

private fun parsePgnLetterToFigure(letter:Char): ChessFigureType = when(letter) {
    'Q' -> ChessFigureType.queen
    'K' -> ChessFigureType.king
    'N' -> ChessFigureType.knight
    'B' -> ChessFigureType.bishop
    'R' -> ChessFigureType.rock
    else -> ChessFigureType.pawn
}

private fun parsePgnStringToPosition(positionString: String?): FigurePosition {
    var column = -1
    var row = -1

    if (positionString != null && positionString.isNotEmpty()) {
        when(positionString.length) {
            1 -> {
                column = parseCharToColumn(positionString.first())
            }
            2 -> {
                column = parseCharToColumn(positionString.first())
                row = parseCharToRow(positionString.last())
            }
        }
    }
    return FigurePosition(row, column)
}

private fun parseCharToColumn(char: Char): Int = when(char) {
    'a' -> 0
    'b' -> 1
    'c' -> 2
    'd' -> 3
    'e' -> 4
    'f' -> 5
    'g' -> 6
    'h' -> 7
    else -> -1
}

private fun parseCharToRow(char: Char): Int =  when(char) {
    '1' -> 7
    '2' -> 6
    '3' -> 5
    '4' -> 4
    '5' -> 3
    '6' -> 2
    '7' -> 1
    '8' -> 0
    else -> -1
}
