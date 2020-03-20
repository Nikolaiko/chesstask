package com.otus.homework.storage

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove
import com.example.core.model.task.PgnMovePair
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessTaskPgnParserKtTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun pgnTestString_parsePgnTestString_parsedMovesEqualsToExpectedMoves() {

        //GIVEN
        val testPgnString = "1. Nf6+ gxf6 2. Bxf7# "

        //WHEN
        val actualPgnMoves = parsePgnString(testPgnString)

        //THEN
        val expectedPgnMoves: List<PgnMovePair> = prepareExpectedMovieSequence()
        assertEquals(actualPgnMoves, expectedPgnMoves)
    }

    private fun prepareExpectedMovieSequence(): List<PgnMovePair> {
        val tempMutableListForMoves = mutableListOf<PgnMovePair>()
        var pair = PgnMovePair(
            PgnMove(
                ChessFigureType.knight,
                ChessFigureColor.w,
                FigurePosition(2, 5),
                null
            ),
            PgnMove(
                ChessFigureType.pawn,
                ChessFigureColor.b,
                FigurePosition(2,5),
                FigurePosition(column = 6),
                true
            )
        )
        tempMutableListForMoves.add(pair)

        pair = PgnMovePair(
            PgnMove(
                ChessFigureType.bishop,
                ChessFigureColor.w,
                FigurePosition(1, 5),
                null,
                true
            )
        )
        tempMutableListForMoves.add(pair)
        return tempMutableListForMoves.toList()
    }
}