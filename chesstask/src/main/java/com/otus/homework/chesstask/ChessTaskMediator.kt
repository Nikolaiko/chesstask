package com.otus.homework.chesstask

import android.content.Context
import com.example.core.mediator.SingleChessTaskMediator
import com.example.core.model.task.ChessTask
import javax.inject.Inject

class ChessTaskMediator @Inject constructor() : SingleChessTaskMediator {
    override fun startOnChessActivity(context: Context, task: ChessTask) {
        ChessTaskActivity.startOnChessActivity(context, task)
    }
}
