package com.otus.homework.app

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.*

fun buildChessTask(): ChessTask = ChessTask("id",
    buildStartingPosition(),
        ChessFigureColor.w,
    buildPgn()
)

private fun buildStartingPosition(): List<ChessFigure> {
    val firstFigurePosition = FigurePosition(1, 1)
    val secondFigurePosition = FigurePosition(1, 5)
    val thirdFigurePosition = FigurePosition(3, 3)

    val firstFigure = ChessFigure(
        ChessFigureType.rock,
        ChessFigureColor.w,
        firstFigurePosition
    )

    val secondFigure = ChessFigure(
        ChessFigureType.knight,
        ChessFigureColor.b,
        secondFigurePosition
    )

    val thirdFigure = ChessFigure(
        ChessFigureType.queen,
        ChessFigureColor.w,
        thirdFigurePosition
    )
    return listOf(firstFigure, secondFigure, thirdFigure)
}

private fun buildPgn(): List<PgnMovePair> {
    val mutable = mutableListOf<PgnMovePair>()
    var pair = PgnMovePair(
        PgnMove(
            ChessFigureType.queen,
            ChessFigureColor.w,
            FigurePosition(3, 5),
            null
        ),
        PgnMove(
            ChessFigureType.knight,
            ChessFigureColor.b,
            FigurePosition(3,4),
            null
        )
    )
    mutable.add(pair)

    pair = PgnMovePair(
        PgnMove(
            ChessFigureType.rock,
            ChessFigureColor.w,
            FigurePosition(1, 5),
            null
        )
    )
    mutable.add(pair)
    return mutable.toList()
}


