package com.otus.homework.chesstask.reducers

import com.example.core.model.task.ChessTask
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.model.ChessTaskNews
import com.otus.homework.chesstask.model.board.BoardAction
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import io.reactivex.Observable

interface BoardReducer {
    val updateBoardPosition: Observable<List<ChessFigureOnBoard>>
    val updateBoardCellSelection: Observable<List<FigurePosition>>
    val updateNews: Observable<ChessTaskNews>
    val applyBoardAction: Observable<BoardAction>

    fun initChessTask(task: ChessTask)
    fun selectFigureById(figureId: String)
    fun selectCellAt(position: FigurePosition)
}
