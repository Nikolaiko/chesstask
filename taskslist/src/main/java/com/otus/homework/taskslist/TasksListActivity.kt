package com.otus.homework.taskslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TasksListActivity : AppCompatActivity() {
    companion object {
        fun startOnTasksListActivity(context: Context) {
            context.startActivity(Intent(context, TasksListActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taskslist)
    }
}
