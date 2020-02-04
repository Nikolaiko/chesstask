package com.otus.homework.network.model.responses

import com.otus.homework.network.model.user.Tokens

data class UserDataResponse (
    val code: Int,
    val message: String?,
    val tokens: Tokens?
)