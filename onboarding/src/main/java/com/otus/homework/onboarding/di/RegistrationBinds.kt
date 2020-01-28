package com.otus.homework.onboarding.di

import com.otus.homework.onboarding.presenters.IRegistrationPresenter
import com.otus.homework.onboarding.presenters.RegistrationPresenter
import com.otus.homework.onboarding.reducer.IRegistrationReducer
import com.otus.homework.onboarding.reducer.RegistrationReducer
import dagger.Binds
import dagger.Module

@Module
abstract class RegistrationBinds {
    @Binds
    abstract fun bindRegistrationReducer(reducer: RegistrationReducer): IRegistrationReducer
    @Binds
    abstract fun bindRegistrationPresenter(presenter: RegistrationPresenter): IRegistrationPresenter
}