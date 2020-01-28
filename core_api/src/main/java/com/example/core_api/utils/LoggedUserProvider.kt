package com.example.core_api.utils

import com.example.core_api.model.UserProfile

interface LoggedUserProvider {
    fun setLoggedUser(loggedUser: UserProfile)
    fun getLoggedUser(): UserProfile?
}