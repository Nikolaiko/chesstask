package com.otus.homework.storage.di

import com.otus.homework.storage.implementations.LoggedUserManager
import com.otus.homework.storage.interfaces.LoggedUserProvider
import dagger.Binds
import dagger.Module

@Module
interface UserDataBindings {
    @Binds
    fun bindLoggedUserComponent(manager: LoggedUserManager): LoggedUserProvider
}