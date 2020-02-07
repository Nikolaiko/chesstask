package com.otus.homework.chesstask.factory

import android.content.Context
import android.widget.ImageView
import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.otus.homework.chesstask.R
import javax.inject.Inject

class ChessViewFactory @Inject constructor(private val context: Context) {
    fun buildFigure(type: ChessFigureType, color: ChessFigureColor): ImageView {
        val pieceImage = ImageView(context)
        pieceImage.setImageResource(getResource(type, color))
        return pieceImage
    }

    private fun getResource(type: ChessFigureType, color: ChessFigureColor) = when {
        (type == ChessFigureType.pawn && color == ChessFigureColor.b) -> R.drawable.svg_black_pawn
        (type == ChessFigureType.pawn && color == ChessFigureColor.w) -> R.drawable.svg_white_pawn
        (type == ChessFigureType.knight && color == ChessFigureColor.b) -> R.drawable.svg_black_knight
        (type == ChessFigureType.knight && color == ChessFigureColor.w) -> R.drawable.svg_white_knight
        (type == ChessFigureType.rock && color == ChessFigureColor.b) -> R.drawable.svg_black_rock
        (type == ChessFigureType.rock && color == ChessFigureColor.w) -> R.drawable.svg_white_rock
        (type == ChessFigureType.bishop && color == ChessFigureColor.b) -> R.drawable.svg_black_bishop
        (type == ChessFigureType.bishop && color == ChessFigureColor.w) -> R.drawable.svg_white_bishop
        (type == ChessFigureType.king && color == ChessFigureColor.b) -> R.drawable.svg_black_king
        (type == ChessFigureType.king && color == ChessFigureColor.w) -> R.drawable.svg_white_king
        (type == ChessFigureType.queen && color == ChessFigureColor.b) -> R.drawable.svg_black_queen
        (type == ChessFigureType.queen && color == ChessFigureColor.w) -> R.drawable.svg_white_queen
        else -> R.drawable.svg_white_pawn
    }
}