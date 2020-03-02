package com.otus.homework.chesstask.model.board

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessBoardStateTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun twoFiguresOnBoard_addAnotherFigure_checkAllFiguresList() {
        //GIVEN
        val testBoardState = ChessBoardState()
        testBoardState.addFigureToBoard(buildFirstExpectedFigure())
        testBoardState.addFigureToBoard(buildSecondExpectedFigure())

        //WHEN
        testBoardState.addFigureToBoard(buildThirdExpectedFigure())

        //THEN
        assert(testBoardState.getFigures().containsAll(
            listOf(
                buildFirstExpectedFigure(),
                buildSecondExpectedFigure(),
                buildThirdExpectedFigure()
            ))
        )
    }

    @Test
    fun emptyBoard_addFigureToBoard_getFigureById() {
        //GIVEN
        val testFigure = buildFirstExpectedFigure()
        val testBoardState = ChessBoardState()

        //WHEN
        testBoardState.addFigureToBoard(testFigure)

        //THEN
        assertEquals(testBoardState.getFigureById(testFigure.id), testFigure)
    }

    @Test
    fun chessBoardWithFigure_getFigureByPgnMove_compareFigureReceivedByPgnMoveWithExpected() {
        //GIVEN
        val testFigure = buildFirstExpectedFigure()
        val testBoardState = ChessBoardState()
        val testPgnMove = PgnMove(
            ChessFigureType.queen,
            ChessFigureColor.w,
            FigurePosition(testFigure.position.row, testFigure.position.column + 3),
            testFigure.position
        )
        testBoardState.addFigureToBoard(testFigure)

        //WHEN
        val actualFigure = testBoardState.getFigureByPgnMove(testPgnMove)

        //THEN
        assertEquals(actualFigure, testFigure)
    }

    @Test
    fun emptyChessBoard_addFigureToBoard_compareFigureReceivedByPositionWithExpected() {
        //GIVEN
        val testFigure = buildFirstExpectedFigure()
        val testBoardState = ChessBoardState()

        //WHEN
        testBoardState.addFigureToBoard(testFigure)

        //THEN
        assertEquals(testBoardState.getFigureByPosition(testFigure.position), testFigure)
    }

    @Test
    fun chessBoardWithFigure_applyAction_checkPositionOfMovedFigure() {
        //GIVEN
        val testFigure = buildFirstExpectedFigure()
        val testBoardState = ChessBoardState()
        val expectedPosition = FigurePosition(testFigure.position.row, testFigure.position.column + 1)
        testBoardState.addFigureToBoard(testFigure)

        val move = BoardAction(
            testFigure,
            testFigure.position,
            expectedPosition,
            null,
            null
        )

        //WHEN
        testBoardState.applyAction(move)

        //THEN
        val movedFigure = testBoardState.getFigureById(testFigure.id)
        assertNotNull(movedFigure)
        assert(movedFigure!!.position == expectedPosition)
    }

    @Test
    fun boardWithFigure_getAvailableForMovePositions_compareResultsWithExpected() {
        //GIVEN
        val testFigure = buildFirstExpectedFigure()
        val testBoardState = ChessBoardState()

        val availableCells = mutableListOf<FigurePosition>()
        availableCells.add(FigurePosition(testFigure.position.row, 0))
        availableCells.add(FigurePosition(0, testFigure.position.column))
        availableCells.add(FigurePosition(0, 0))
        availableCells.add(FigurePosition(testFigure.position.row + 1, 0))

        for (i in 2..7) {
            availableCells.add(FigurePosition(testFigure.position.row, i))
        }

        for (i in 2..7) {
            availableCells.add(FigurePosition(i, testFigure.position.column))
        }

        for (i in 2..7) {
            availableCells.add(FigurePosition(i, i))
        }
        testBoardState.addFigureToBoard(testFigure)

        //WHEN
        val cells = testBoardState.getAvailableCellsForFigure(testFigure.id)

        //THEN
        assert(cells.containsAll(availableCells))
    }

    private fun buildFirstExpectedFigure(): ChessFigureOnBoard = ChessFigureOnBoard(
        "id1",
        ChessFigureColor.w,
        FigurePosition(1, 1),
        ChessFigureType.queen
    )

    private fun buildSecondExpectedFigure(): ChessFigureOnBoard = ChessFigureOnBoard(
        "id2",
        ChessFigureColor.w,
        FigurePosition(1, 5),
        ChessFigureType.queen
    )

    private fun buildThirdExpectedFigure(): ChessFigureOnBoard = ChessFigureOnBoard(
        "id3",
        ChessFigureColor.b,
        FigurePosition(3, 3),
        ChessFigureType.queen
    )
}