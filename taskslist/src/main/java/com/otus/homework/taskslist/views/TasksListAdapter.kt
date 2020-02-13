package com.otus.homework.taskslist.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.task.ChessTaskShortInfo
import com.otus.homework.taskslist.R
import com.otus.homework.taskslist.RowClickCallback

class TasksListAdapter : RecyclerView.Adapter<TaskCellViewHolder>() {

    var tasks:List<ChessTaskShortInfo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var rowClickCallback:RowClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_task_cell, parent, false)
        return TaskCellViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskCellViewHolder, position: Int) {
        holder.taskDescription.text = tasks[position].description
        holder.mainLayout.setOnClickListener {
            rowClickCallback?.invoke(tasks[position])
        }
    }
}
