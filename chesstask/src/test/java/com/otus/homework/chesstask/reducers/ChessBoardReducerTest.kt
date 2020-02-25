package com.otus.homework.chesstask.reducers

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.*
import com.otus.homework.chesstask.RxJavaRule
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import com.otus.homework.chesstask.presenters.BoardPresenter
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.creation.MockSettingsImpl
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessBoardReducerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    private val firstFigurePosition = FigurePosition(1, 1)
    private val secondFigurePosition = FigurePosition(1, 5)
    private val thirdFigurePosition = FigurePosition(3, 3)

    private val firstFigure = ChessFigure(
        ChessFigureType.rock,
        ChessFigureColor.w,
        firstFigurePosition
    )

    private val secondFigure = ChessFigure(
        ChessFigureType.knight,
        ChessFigureColor.b,
        secondFigurePosition
    )

    private val thirdFigure = ChessFigure(
        ChessFigureType.queen,
        ChessFigureColor.w,
        thirdFigurePosition
    )

    private lateinit var reducer: ChessBoardReducer
    private lateinit var testTask: ChessTask

    @Before
    fun initTask() {
        testTask = ChessTask(
            "testtask",
            buildStartingPositions(),
            ChessFigureColor.w,
            buildPgn()
        )
        reducer = ChessBoardReducer()
    }

    @Test
    fun initChessTask() {
//        reducer.updateBoardPosition
//            .test()
//            .assertValue(buildUpdateBoard())
//            reducer.initChessTask(testTask)
    }

    @Test
    fun selectFigureById() {
    }

    @Test
    fun selectCellAt() {
    }

    private fun buildStartingPositions(): List<ChessFigure> = listOf(firstFigure, secondFigure, thirdFigure)

    private fun buildUpdateBoard(): List<ChessFigureOnBoard> = listOf(
            ChessFigureOnBoard.convert(firstFigure),
            ChessFigureOnBoard.convert(secondFigure),
            ChessFigureOnBoard.convert(thirdFigure)
        )

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
}