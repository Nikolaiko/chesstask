package com.example.core_api.model.task

import com.example.core_api.model.enums.ChessFigureColor

data class ChessTask(val id:String,
                     val startingPositions:List<ChessFigure>,
                     val activeColor: ChessFigureColor
)