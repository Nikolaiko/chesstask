package com.otus.homework.chesstask.model

data class ChessTaskNews(
    val newsId: ChessTaskMessageId,
    val message: String = ""
)