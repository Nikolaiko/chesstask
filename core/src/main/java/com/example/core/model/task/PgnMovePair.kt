package com.example.core.model.task

data class PgnMovePair (
    val whiteMove: PgnMove?,
    val blackMove: PgnMove? = null
)