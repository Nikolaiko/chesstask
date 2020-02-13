package com.example.core.mediator

import android.content.Context
import com.example.core.model.task.ChessTask

interface SingleChessTaskMediator  {
    fun startOnChessActivity(context: Context, task: ChessTask)
}
