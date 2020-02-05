package com.otus.homework.network.model.chesstasks

import com.google.gson.annotations.SerializedName

data class ChessTaskShortData(
    @SerializedName("id")
    val id:String,
    @SerializedName("name")
    val name:String
)