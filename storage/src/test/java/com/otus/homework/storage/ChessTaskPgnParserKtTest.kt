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

    private val pgnTest1 = "1. Nf6+ gxf6 2. Bxf7# "
    private val pgnTest2 = "1. Qb8+ Nxb8 2. Rd8#   "

    private lateinit var pgnMoves1: List<PgnMovePair>
    private lateinit var pgnMoves2: List<PgnMovePair>

    @Before
    fun init() {
        initFirst()
        initSecond()
    }

    @Test
    fun parsePgnStringTest() {
        val first = parsePgnString(pgnTest1)
        val second = parsePgnString(pgnTest2)

        assertEquals(first, pgnMoves1)
        assertEquals(second, pgnMoves2)
    }

    private fun initFirst() {
        val mutable = mutableListOf<PgnMovePair>()
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
        mutable.add(pair)

        pair = PgnMovePair(
            PgnMove(
                ChessFigureType.bishop,
                ChessFigureColor.w,
                FigurePosition(1, 5),
                null,
                true
            )
        )
        mutable.add(pair)
        pgnMoves1 = mutable.toList()
    }

    private fun initSecond() {
        val mutable = mutableListOf<PgnMovePair>()
        var pair = PgnMovePair(
            PgnMove(
                ChessFigureType.queen,
                ChessFigureColor.w,
                FigurePosition(0, 1),
                null
            ),
            PgnMove(
                ChessFigureType.knight,
                ChessFigureColor.b,
                FigurePosition(0,1),
                null,
                true
            )
        )
        mutable.add(pair)

        pair = PgnMovePair(
            PgnMove(
                ChessFigureType.rock,
                ChessFigureColor.w,
                FigurePosition(0, 3),
                null
            )
        )
        mutable.add(pair)
        pgnMoves2 = mutable.toList()
    }
}