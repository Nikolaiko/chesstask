package com.example.core_api.model

data class UserProfile(
    val username:String,
    val token:String?,
    val refreshToken:String?)