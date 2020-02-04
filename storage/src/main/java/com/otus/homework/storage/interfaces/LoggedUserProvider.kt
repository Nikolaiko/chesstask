package com.otus.homework.storage.interfaces

import com.example.core.model.user.UserProfile
import com.example.core.model.user.UserTokens

interface LoggedUserProvider {
    fun setLoggedUser(loggedUser: UserProfile)
    fun getLoggedUser(): UserProfile?
    fun setLoggedUserTokens(tokens: UserTokens)
    fun getLoggedUserTokens(): UserTokens?
}