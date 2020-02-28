package com.otus.homework.storage

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.ChessFigure
import com.example.core.model.task.FigurePosition
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessTaskFenParserKtTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun testFenString_parseTestFenString_receivedStartingColorEqualsToExpectedStartingColor() {

        //GIVEN
        val testFenString = "r2qkb1r/pp2nppp/3p4/2pNN1B1/2BnP3/3P4/PPP2PPP/R2bK2R b KQkq - 1 0"

        //WHEN
        val actualColor = ChessFigureColor.b

        assertEquals(ChessFigureColor.b, actualColor)
    }

    @Test
    fun testFenString_parseTestFenString_parsedFiguresListEqualsToExpectedFiguresList() {

        //GIVEN
        val testFenString = "r2qkb1r/8/8/8/8/8/8/R2bK2R w KQkq - 1 0"

        //WHEN
        val actualPositions = getStartingPositions(testFenString)

        //THEN
        val expectedStartedPositions = createExpectedStartedPositions()
        assertEquals(actualPositions, expectedStartedPositions)
    }

    private fun createExpectedStartedPositions(): List<ChessFigure> {
        val mutable:MutableList<ChessFigure> = mutableListOf()

        mutable.add(
            ChessFigure(
                ChessFigureType.rock,
                ChessFigureColor.b,
                FigurePosition(0,0)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.queen,
                ChessFigureColor.b,
                FigurePosition(0,3)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.king,
                ChessFigureColor.b,
                FigurePosition(0,4)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.bishop,
                ChessFigureColor.b,
                FigurePosition(0,5)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.rock,
                ChessFigureColor.b,
                FigurePosition(0,7)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.rock,
                ChessFigureColor.w,
                FigurePosition(7,0)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.bishop,
                ChessFigureColor.b,
                FigurePosition(7,3)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.king,
                ChessFigureColor.w,
                FigurePosition(7,4)
            )
        )

        mutable.add(
            ChessFigure(
                ChessFigureType.rock,
                ChessFigureColor.w,
                FigurePosition(7,7)
            )
        )

        return mutable.toList()
    }

}