package com.otus.homework.onboarding.di

import com.otus.homework.taskslist.presenters.UserRegistrationPresenter
import com.otus.homework.taskslist.presenters.RegistrationPresenter
import com.otus.homework.onboarding.reducer.UserRegistrationReducer
import com.otus.homework.onboarding.reducer.RegistrationReducer
import dagger.Binds
import dagger.Module

@Module
abstract class RegistrationBinds {
    @Binds
    abstract fun bindRegistrationReducer(reducer: RegistrationReducer): UserRegistrationReducer
    @Binds
    abstract fun bindRegistrationPresenter(presenter: RegistrationPresenter): UserRegistrationPresenter
}
