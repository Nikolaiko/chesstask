package com.otus.homework.chesstask.model.board

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessBoardStateTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val firstFigurePosition = FigurePosition(1, 1)
    private val secondFigurePosition = FigurePosition(1, 5)
    private val thirdFigurePosition = FigurePosition(3, 3)

    private val firstChessFigure = ChessFigureOnBoard(
        "id1",
        ChessFigureColor.w,
        firstFigurePosition,
        ChessFigureType.queen
    )
    private val secondChessFigure = ChessFigureOnBoard(
        "id2",
        ChessFigureColor.w,
        secondFigurePosition,
        ChessFigureType.queen
    )
    private val thirdChessFigure = ChessFigureOnBoard(
        "id3",
        ChessFigureColor.b,
        thirdFigurePosition,
        ChessFigureType.queen
    )
    private val listWithFigures: List<ChessFigureOnBoard> = listOf(
        firstChessFigure,
        secondChessFigure,
        thirdChessFigure
    )


    private lateinit var chessBoardState: ChessBoardState

    @Test
    fun threeFiguresOnBoard_addAnotherFigure_checkAllFiguresList() {
        //GIVEN
        chessBoardState = ChessBoardState()
        chessBoardState.addFigureToBoard(firstChessFigure)
        chessBoardState.addFigureToBoard(secondChessFigure)

        //WHEN
        chessBoardState.addFigureToBoard(thirdChessFigure)

        //THEN
        assert(chessBoardState.getFigures().containsAll(listWithFigures))
    }

    @Test
    fun getFigureById() {
        assertEquals(firstChessFigure, chessBoardState.getFigureById(firstChessFigure.id))
        assertEquals(secondChessFigure, chessBoardState.getFigureById(secondChessFigure.id))
        assertEquals(thirdChessFigure, chessBoardState.getFigureById(thirdChessFigure.id))
    }

    @Test
    fun getFigures() {
        assert(chessBoardState.getFigures().containsAll(listWithFigures))
    }

    @Test
    fun getFigureByPgnMove() {
        val pgnWhitMoveFirst = PgnMove(
            ChessFigureType.queen,
            ChessFigureColor.w,
            thirdFigurePosition,
            firstFigurePosition,
            true
        )

        val pgnWhitMoveSecond = PgnMove(
            ChessFigureType.queen,
            ChessFigureColor.w,
            thirdFigurePosition,
            secondFigurePosition,
            true
        )

        val pgnBlackMoveFirst = PgnMove(
            ChessFigureType.queen,
            ChessFigureColor.b,
            firstFigurePosition,
            thirdFigurePosition,
            true
        )

        val pgnBlackMoveSecond = PgnMove(
            ChessFigureType.queen,
            ChessFigureColor.b,
            FigurePosition(5, 5),
            null
        )

        assertEquals(chessBoardState.getFigureByPgnMove(pgnWhitMoveFirst), firstChessFigure)
        assertEquals(chessBoardState.getFigureByPgnMove(pgnWhitMoveSecond), secondChessFigure)
        assertEquals(chessBoardState.getFigureByPgnMove(pgnBlackMoveFirst), thirdChessFigure)
        assertEquals(chessBoardState.getFigureByPgnMove(pgnBlackMoveSecond), thirdChessFigure)
    }

    @Test
    fun getFigureByPosition() {
        assertEquals(firstChessFigure, chessBoardState.getFigureByPosition(firstFigurePosition))
        assertEquals(secondChessFigure, chessBoardState.getFigureByPosition(secondFigurePosition))
    }

    @Test
    fun applyAction() {
        val move = BoardAction(
            firstChessFigure,
            firstFigurePosition,
            thirdFigurePosition,
            thirdChessFigure
        )

        val movedFirstFigure = ChessFigureOnBoard(
            firstChessFigure.id,
            firstChessFigure.color,
            thirdFigurePosition,
            firstChessFigure.figureType
        )
        val afterMoveState = listOf(movedFirstFigure, secondChessFigure)

        chessBoardState.applyAction(move)
        assert(chessBoardState.getFigures().containsAll(afterMoveState))
    }

    @Test
    fun getAvailableCellsForFigure() {
        val availableCells = mutableListOf<FigurePosition>()

        availableCells.add(FigurePosition(firstChessFigure.position.row, 0))
        availableCells.add(FigurePosition(0, firstChessFigure.position.column))
        availableCells.add(FigurePosition(0, 0))
        availableCells.add(FigurePosition(0, 2))
        availableCells.add(FigurePosition(2, 0))

        for (i in 2..4) {
            availableCells.add(FigurePosition(firstChessFigure.position.row, i))
        }

        for (i in 2..7) {
            availableCells.add(FigurePosition(i, firstChessFigure.position.column))
        }

        for (i in 2..2) {
            availableCells.add(FigurePosition(i, i))
        }


        val cells = chessBoardState.getAvailableCellsForFigure(firstChessFigure.id)
        assert(cells.containsAll(availableCells))
    }
}