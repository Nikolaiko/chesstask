package com.otus.homework.chesstask.presenters

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.*
import com.otus.homework.chesstask.RxJavaRule
import com.otus.homework.chesstask.views.ChessTaskView
import org.junit.Test

import org.junit.Rule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessBoardPresenterTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Test
    fun testChessTask_initPresenterWithTask_reducerFunctionCalled() {
        //GIVEN
        val expectedTask = ChessTask(
            "testtask",
            buildExpectedPositions(),
            ChessFigureColor.w,
            buildExpectedPgn()
        )
        val reducer = ReducerStub()
        val presenter = ChessBoardPresenter(reducer)

        //WHEN
        presenter.setBoardTask(expectedTask)

        //THEN
        assert(reducer.initChessTaskCalledWith(expectedTask))
    }

    private fun buildExpectedPositions(): List<ChessFigure> {
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

    private fun buildExpectedPgn(): List<PgnMovePair> {
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