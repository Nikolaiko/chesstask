package com.otus.homework.network.model.responses

data class Response<T>(
    val code: Int,
    val message: String?,
    val responseObject: T?
)