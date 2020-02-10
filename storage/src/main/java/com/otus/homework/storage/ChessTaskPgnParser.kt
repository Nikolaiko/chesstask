package com.otus.homework.storage

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove
import com.example.core.model.task.PgnMovePair

fun parsePgnString(pgn: String): List<PgnMovePair> {
    println("PGN : $pgn")

    val pgnTurns: MutableList<PgnMovePair> = mutableListOf()
    val chessMoveSplitRegex = Regex("\\d{1,3}[.]")
    val splittedPgn = pgn.split(chessMoveSplitRegex).filter { it.isNotEmpty() }

    val trimmedPgn = mutableListOf<String>()
    splittedPgn.forEach { trimmedPgn.add(it.trim()) }

    trimmedPgn.forEach {
        pgnTurns.add(parseSingleTurn(it))
    }
    println(pgnTurns)
    return pgnTurns.toList()
}

private fun parseSingleTurn(turn: String): PgnMovePair {
    val splittedMoves = turn.split(" ")
    val whiteMoveString = splittedMoves[0].trim('=', '+', '-', '/', '!', '?', '#', ' ')
    val whiteMove = parsePgnMove(whiteMoveString, ChessFigureColor.w)

    var blackMove: PgnMove? = null
    if (splittedMoves.size > 1) {
        blackMove = parsePgnMove(splittedMoves[1], ChessFigureColor.b)
    }
    return PgnMovePair(whiteMove, blackMove)
}

private fun parsePgnMove(move: String, color: ChessFigureColor): PgnMove {
    var figureType = ChessFigureType.pawn
    var attackEnemyFigure = false

    if (move[0].isUpperCase()) {
        figureType = parsePgnLetterToFigure(move[0])
    }

    var withoutFigureType = move.substring(1)
    if (withoutFigureType.contains('x')) {
        attackEnemyFigure = true
        withoutFigureType = withoutFigureType.substring(1)
    }
    val position = parsePgnStringToPosition(withoutFigureType)

    return PgnMove(
        figureType,
        color,
        position,
        attackEnemyFigure
    )
}

private fun parsePgnStringToPosition(positionString: String): FigurePosition {
    val column = when(positionString.first()) {
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
    val row = when(positionString.last()) {
        '1' -> 0
        '2' -> 1
        '3' -> 2
        '4' -> 3
        '5' -> 4
        '6' -> 5
        '7' -> 6
        '8' -> 7
        else -> -1
    }
    return FigurePosition(row, column)
}

private fun parsePgnLetterToFigure(letter:Char): ChessFigureType = when(letter) {
    'Q' -> ChessFigureType.queen
    'K' -> ChessFigureType.king
    'N' -> ChessFigureType.knight
    'B' -> ChessFigureType.bishop
    'R' -> ChessFigureType.rock
    else -> ChessFigureType.pawn
}
