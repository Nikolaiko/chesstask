package com.otus.homework.storage.interfaces

import com.example.core.model.UserProfile

interface LoggedUserProvider {
    fun setLoggedUser(loggedUser: UserProfile)
    fun getLoggedUser(): UserProfile?
}