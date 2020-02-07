package com.example.core.model.task

data class FigurePosition (
    val row:Int = -1,
    val column:Int = -1
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other::class != this::class) return false
        val secondPosition = other as FigurePosition
        return secondPosition.column == column && secondPosition.row == row
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}