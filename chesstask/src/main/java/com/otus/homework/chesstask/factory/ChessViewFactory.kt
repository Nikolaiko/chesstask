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

    private fun getResource(type: ChessFigureType, color: ChessFigureColor) = when(color) {
        ChessFigureColor.w -> getWhiteResource(type)
        ChessFigureColor.b -> getBlackResource(type)
    }

    private fun getBlackResource(type: ChessFigureType) = when(type) {
        ChessFigureType.pawn -> R.drawable.svg_black_pawn
        ChessFigureType.knight -> R.drawable.svg_black_knight
        ChessFigureType.rock -> R.drawable.svg_black_rock
        ChessFigureType.bishop -> R.drawable.svg_black_bishop
        ChessFigureType.king -> R.drawable.svg_black_king
        ChessFigureType.queen -> R.drawable.svg_black_queen
    }

    private fun getWhiteResource(type: ChessFigureType) = when(type) {
        ChessFigureType.pawn -> R.drawable.svg_white_pawn
        ChessFigureType.knight -> R.drawable.svg_white_knight
        ChessFigureType.rock -> R.drawable.svg_white_rock
        ChessFigureType.bishop -> R.drawable.svg_white_bishop
        ChessFigureType.king -> R.drawable.svg_white_king
        ChessFigureType.queen -> R.drawable.svg_white_queen
    }
}
