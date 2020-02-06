package com.otus.homework.chesstask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.model.task.ChessTask

class ChessTaskActivity : AppCompatActivity() {
    companion object {
        private var selectedTask:ChessTask? = null

        fun startOnChessActivity(context: Context, task: ChessTask) {
            selectedTask = task
            context.startActivity(Intent(context, ChessTaskActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chesstask)
    }
}