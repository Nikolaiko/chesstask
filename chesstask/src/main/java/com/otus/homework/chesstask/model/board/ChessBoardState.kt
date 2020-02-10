package com.otus.homework.chesstask.model.board

import android.graphics.Point
import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard


class ChessBoardState {
    private val figures: MutableMap<String, ChessFigureOnBoard> = mutableMapOf()
    private val history: MutableList<BoardAction> = mutableListOf()

    fun addFigureToBoard(figure: ChessFigureOnBoard) {
        if (!figures.containsKey(figure.id)) {
            figures[figure.id] = figure
        }
    }

    fun getFigureById(id: String) = figures[id]
    fun getFigures() = figures.values.toList()
    fun getFigureByPosition(targetPosition: FigurePosition): ChessFigureOnBoard? {
        var foundFigure: ChessFigureOnBoard? = null
        for (currentFigure in figures) {
            if (currentFigure.value.position == targetPosition) {
                foundFigure = currentFigure.value
                break
            }
        }
        return foundFigure
    }

    fun applyAction(action: BoardAction) {
        history.add(action)

        val removedFigure = action.removedFigure
        if (removedFigure != null) {
            figures.remove(removedFigure.id)
        }

        val movedFigure = figures[action.figure.id]
        if (movedFigure != null) {
            figures[movedFigure.id] = movedFigure.copy(position = action.endPosition)
        }
    }

    fun getAvailableCellsForFigure(figureId:String): List<FigurePosition> {
        val currentFigure = figures[figureId]
        var availableCells: List<FigurePosition> = emptyList()
        if (currentFigure != null) {
           availableCells = when(currentFigure.figureType) {
               ChessFigureType.bishop -> getAvailableCellsForBishop(currentFigure.position, currentFigure.color)
               ChessFigureType.rock -> getAvailableCellsForRock(currentFigure.position, currentFigure.color)
               ChessFigureType.knight -> getAvailableCellsForKnight(currentFigure.position, currentFigure.color)
               ChessFigureType.pawn -> getAvailableCellsForPawn(currentFigure.position, currentFigure.color)
               ChessFigureType.queen -> getAvailableCellsForQueen(currentFigure.position, currentFigure.color)
               ChessFigureType.king -> getAvailableCellsForKing(currentFigure.position, currentFigure.color)
           }
        }
        return availableCells
    }

    private fun getAvailableCellsForPawn(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = mutableListOf<FigurePosition>()

        if (color == ChessFigureColor.b) {
            var newPosition = FigurePosition(
                position.row + 1,
                position.column
            )
            if (isCellAvailableForColor(newPosition, color)) {
                totalCells.add(newPosition)
            }

            newPosition = FigurePosition(
                position.row + 1,
                position.column - 1
            )
            if (isEnemyFigureOnCell(newPosition, color)) {
                totalCells.add(newPosition)
            }

            newPosition = FigurePosition(
                position.row + 1,
                position.column + 1
            )
            if (isEnemyFigureOnCell(newPosition, color)) {
                totalCells.add(newPosition)
            }
        } else {
            var newPosition = FigurePosition(
                position.row - 1,
                position.column
            )
            if (isCellAvailableForColor(newPosition, color)) {
                totalCells.add(newPosition)
            }

            newPosition = FigurePosition(
                position.row - 1,
                position.column - 1
            )
            if (isEnemyFigureOnCell(newPosition, color)) {
                totalCells.add(newPosition)
            }

            newPosition = FigurePosition(
                position.row - 1,
                position.column + 1
            )
            if (isEnemyFigureOnCell(newPosition, color)) {
                totalCells.add(newPosition)
            }
        }
        return totalCells
    }

    private fun getAvailableCellsForKing(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = mutableListOf<FigurePosition>()

        var newPosition = FigurePosition(
            position.row + 1,
            position.column + 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 1,
            position.column - 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 1,
            position.column + 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row + 1,
            position.column - 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row + 1,
            position.column
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 1,
            position.column
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row,
            position.column + 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row,
            position.column - 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        return totalCells
    }

    private fun getAvailableCellsForKnight(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = mutableListOf<FigurePosition>()

        var newPosition = FigurePosition(
            position.row + 2,
            position.column + 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row + 2,
            position.column - 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 2,
            position.column + 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 2,
            position.column - 1
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        /*-------*/

        newPosition = FigurePosition(
            position.row + 1,
            position.column + 2
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 1,
            position.column + 2
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row - 1,
            position.column - 2
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        newPosition = FigurePosition(
            position.row + 1,
            position.column - 2
        )
        if (isCellAvailableForColor(newPosition, color)) {
            totalCells.add(newPosition)
        }

        return totalCells
    }

    private fun getAvailableCellsForBishop(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = (checkStraitPath(Point(-1, -1), position, color))
        totalCells.addAll(checkStraitPath(Point(1, 1), position, color))
        totalCells.addAll(checkStraitPath(Point(-1, 1), position, color))
        totalCells.addAll(checkStraitPath(Point(1, -1), position, color))
        return totalCells
    }

    private fun getAvailableCellsForQueen(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = checkStraitPath(Point(0, 1), position, color)
        totalCells.addAll(checkStraitPath(Point(0, -1), position, color))
        totalCells.addAll(checkStraitPath(Point(1, 0), position, color))
        totalCells.addAll(checkStraitPath(Point(-1, 0), position, color))
        totalCells.addAll(checkStraitPath(Point(-1, -1), position, color))
        totalCells.addAll(checkStraitPath(Point(1, 1), position, color))
        totalCells.addAll(checkStraitPath(Point(-1, 1), position, color))
        totalCells.addAll(checkStraitPath(Point(1, -1), position, color))
        return totalCells
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

    private fun isCellAvailableForColor(
        position: FigurePosition,
        color: ChessFigureColor
    ): Boolean {
        var available = true
        if (!isCellOnBoard(position)) {
            available = false
        } else if (isCellFree(position)) {
            available = true
        } else {
            val figureOnCell = getFigureForCell(position)
            available = figureOnCell != null && figureOnCell.color != color
        }
        return available
    }

    private fun isEnemyFigureOnCell(position: FigurePosition, color: ChessFigureColor): Boolean {
        val figureOnCell = getFigureForCell(position)
        return figureOnCell != null && figureOnCell.color != color
    }

    private fun getFigureForCell(position: FigurePosition): ChessFigureOnBoard? {
        var findedFigure: ChessFigureOnBoard? = null
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