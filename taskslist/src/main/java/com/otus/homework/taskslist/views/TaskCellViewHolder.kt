package com.otus.homework.taskslist.views

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.otus.homework.taskslist.R

class TaskCellViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    val taskDescription: TextView = view.findViewById(R.id.taskDescription)
    val mainLayout: ConstraintLayout = view.findViewById(R.id.parentLayout)
}
