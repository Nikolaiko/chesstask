package com.otus.homework.network.model.responses

import com.otus.homework.network.model.chesstasks.ChessTaskData

data class ChessTaskResponse(
    val code: Int,
    val message: String?,
    val taskData: ChessTaskData?
)