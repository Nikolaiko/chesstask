package com.otus.homework.network.model.user

import com.google.gson.annotations.SerializedName

data class Tokens (
    @SerializedName("accessToken")
    val accessToken:String
)