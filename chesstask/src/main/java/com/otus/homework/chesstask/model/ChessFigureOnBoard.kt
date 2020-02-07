package com.otus.homework.chesstask.model

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.ChessFigure
import com.example.core.model.task.FigurePosition
import java.util.*

data class ChessFigureOnBoard(
    val id:String,
    val color:ChessFigureColor,
    val position:FigurePosition,
    val figureType:ChessFigureType
) {
    companion object {
        fun convert(from: ChessFigure): ChessFigureOnBoard {
            return ChessFigureOnBoard(
                UUID.randomUUID().toString(),
                from.color,
                from.position,
                from.figureType
            )
        }
    }
}