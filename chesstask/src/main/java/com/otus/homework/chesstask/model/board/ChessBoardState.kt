package com.otus.homework.chesstask.model.board

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition
import com.example.core.model.task.PgnMove
import com.otus.homework.chesstask.MAX_BOARD_INDEX
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
    fun getFigureByPgnMove(move: PgnMove): ChessFigureOnBoard? {
        var foundFigure: ChessFigureOnBoard? = null
        var neededFigures = figures.filter { it.value.color == move.figureColor && it.value.figureType == move.figureType }
        val startMove = move.start ?: FigurePosition()

        if (startMove.row != -1) {
            neededFigures = neededFigures.filter { it.value.position.row == startMove.row }
        }
        if (startMove.column != -1) {
            neededFigures = neededFigures.filter { it.value.position.column == startMove.column }
        }
        if (neededFigures.size == 1) return neededFigures.values.first()
        for (currentFigure in neededFigures) {
            val cells = getAvailableCellsForFigure(currentFigure.key)
            for (currentCell in cells) {
                if (currentCell == move.destination) {
                    foundFigure = currentFigure.value
                    break
                }
            }
        }
        return foundFigure
    }

    fun getFigureByPosition(position: FigurePosition): ChessFigureOnBoard? {
        var foundedFigure:ChessFigureOnBoard? = null
        val figuresOnPosition = figures.filter { it.value.position == position }.values
        if (figuresOnPosition.isNotEmpty()) {
            foundedFigure = figuresOnPosition.first()
        }
        return foundedFigure
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

    fun undoLastAction(): BoardAction? {
        if (history.isNotEmpty()) {
            val lastMove = history.removeAt(history.size - 1)
            val reversed = lastMove.reverse()
            if (reversed.addedFigure != null) {
                figures[reversed.addedFigure.id] = reversed.addedFigure
            }
            applyAction(reversed)
            return  reversed
        }
        return null
    }

    fun clearState() {
        figures.clear()
        history.clear()
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
        val totalCells = (checkStraitPath(FigurePosition(-1, -1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(1, 1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(-1, 1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(1, -1), position, color))
        return totalCells
    }

    private fun getAvailableCellsForQueen(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = checkStraitPath(FigurePosition(0, 1), position, color)
        totalCells.addAll(checkStraitPath(FigurePosition(0, -1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(1, 0), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(-1, 0), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(-1, -1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(1, 1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(-1, 1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(1, -1), position, color))
        return totalCells
    }

    private fun getAvailableCellsForRock(position: FigurePosition, color: ChessFigureColor): List<FigurePosition> {
        val totalCells = checkStraitPath(FigurePosition(0, 1), position, color)
        totalCells.addAll(checkStraitPath(FigurePosition(0, -1), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(1, 0), position, color))
        totalCells.addAll(checkStraitPath(FigurePosition(-1, 0), position, color))
        return totalCells
    }

    private fun checkStraitPath(
        coff:FigurePosition,
        position: FigurePosition,
        color: ChessFigureColor
    ): MutableList<FigurePosition> {
        var nextStep = true
        var currentPosition = position
        val availableCells: MutableList<FigurePosition> = mutableListOf()

        while (nextStep) {
            currentPosition = FigurePosition(
                currentPosition.row + coff.row,
                currentPosition.column + coff.column
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
        return position.row in 0..MAX_BOARD_INDEX && position.column in 0..MAX_BOARD_INDEX
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
