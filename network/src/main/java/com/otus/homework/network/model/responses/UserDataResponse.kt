package com.otus.homework.network.model.responses

import com.otus.homework.model.user.UserData

data class UserDataResponse (
    val code: Int,
    val message: String?,
    val userData: UserData?
)