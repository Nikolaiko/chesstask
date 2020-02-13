package com.otus.homework.chesstask.model.figure

import android.widget.ImageView
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.FigurePosition

data class ChessFigureView(
    val id: String,
    val imageView: ImageView,
    val position: FigurePosition,
    val type: ChessFigureType
)
