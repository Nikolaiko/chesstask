package com.otus.homework.taskslist.model

import com.example.core.model.task.ChessTaskShortInfo

data class TasksListState(
    val loadingActive:Boolean = false,
    val loadedTasks:List<ChessTaskShortInfo> = emptyList()
)