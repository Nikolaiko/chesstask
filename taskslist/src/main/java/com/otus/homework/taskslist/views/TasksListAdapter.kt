package com.otus.homework.taskslist.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.task.ChessTaskShortInfo
import com.otus.homework.taskslist.R
import com.otus.homework.taskslist.RowClickCallback

class TasksListAdapter : RecyclerView.Adapter<TaskCellViewHolder>() {
    companion object {
        private const val MAX_DESCRIPTION_PARTS: Int = 3
    }

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
        val splittedDescription = tasks[position].description.split(",")
        holder.taskDescription.text = splittedDescription[0].trim()
        holder.taskPlace.text = splittedDescription[1].trim()
        if (splittedDescription.size == MAX_DESCRIPTION_PARTS) {
            holder.taskYear.text = splittedDescription[2].trim()
        }
        holder.mainLayout.setOnClickListener {
            rowClickCallback?.invoke(tasks[position])
        }
    }
}
