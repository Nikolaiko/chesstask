package com.otus.homework.model.user

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("email")
    val email:String = "",
    @SerializedName("password")
    val password:String = ""
)