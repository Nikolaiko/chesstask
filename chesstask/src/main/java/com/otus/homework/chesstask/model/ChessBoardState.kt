package com.otus.homework.chesstask.model

import android.graphics.Point
import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.task.FigurePosition


class ChessBoardState {
    private val figures: MutableMap<String, ChessFigureOnBoard> = mutableMapOf()
    private val history: MutableList<ChessMove> = mutableListOf()

    fun addFigureToBoard(figure: ChessFigureOnBoard) {
        if (!figures.containsKey(figure.id)) {
            figures[figure.id] = figure
        }
    }

    fun getFigures() = figures.values.toList()

    fun getAvailableCellsForFigure(figureId:String): List<FigurePosition> {
        val currentFigure = figures[figureId]
        val availableCells: MutableList<FigurePosition> = mutableListOf()
        if (currentFigure != null) {
            availableCells.addAll(getAvailableCellsForRock(currentFigure.position, currentFigure.color))
        }
        return availableCells
    }

    private fun getAvailableCellsForRock(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = checkStraitPath(Point(0, 1), position, color)
        totalCells.addAll(checkStraitPath(Point(0, -1), position, color))
        totalCells.addAll(checkStraitPath(Point(1, 0), position, color))
        totalCells.addAll(checkStraitPath(Point(-1, 0), position, color))
        return totalCells
    }

    private fun checkStraitPath(
        coff:Point,
        position: FigurePosition,
        color: ChessFigureColor
    ): MutableList<FigurePosition> {
        var nextStep = true
        var currentPosition = position
        val availableCells: MutableList<FigurePosition> = mutableListOf()

        while (nextStep) {
            currentPosition = FigurePosition(
                currentPosition.row + coff.x,
                currentPosition.column + coff.y
            )
            if (!isCellOnBoard(currentPosition)) {
                nextStep = false
            } else if (isCellFree(currentPosition)) {
                availableCells.add(currentPosition)
            } else {
                nextStep = false

                val figureOnCell = getFigureForCell(currentPosition)
                if (figureOnCell != null && figureOnCell.color != color) {
                    availableCells.add(currentPosition)
                }
            }
        }
        return availableCells
    }

    private fun getFigureForCell(position: FigurePosition): ChessFigureOnBoard? {
        var findedFigure:ChessFigureOnBoard? = null
        for (pair in figures) {
            if (pair.value.position.equals(position)) {
                findedFigure = pair.value
                break
            }
        }
        return findedFigure
    }

    private fun isCellOnBoard(position: FigurePosition): Boolean {
        return position.row in 0..7 && position.column in 0..7
    }

    private fun isCellFree(cellPosition:FigurePosition): Boolean {
        var isFree = true
        for (pair in figures) {
            if (pair.value.position.equals(cellPosition)) {
                isFree = false
                break
            }
        }
        return isFree
    }
}