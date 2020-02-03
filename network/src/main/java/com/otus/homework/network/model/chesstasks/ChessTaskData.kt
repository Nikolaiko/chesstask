package com.otus.homework.network.model.chesstasks

import com.google.gson.annotations.SerializedName

data class ChessTaskData (
    @SerializedName("id")
    val id:String,
    @SerializedName("fen")
    val fen:String,
    @SerializedName("pgn")
    val pgn:String

)