package com.otus.homework.chessclient.onboarding.di

import com.otus.homework.chessclient.onboarding.presenters.IRegistrationPresenter
import com.otus.homework.chessclient.onboarding.presenters.RegistrationPresenter
import com.otus.homework.chessclient.onboarding.reducer.IRegistrationReducer
import com.otus.homework.chessclient.onboarding.reducer.RegistrationReducer
import dagger.Binds
import dagger.Module

@Module
abstract class RegistrationBinds {
    @Binds
    abstract fun bindRegistrationReducer(reducer: RegistrationReducer): IRegistrationReducer
    @Binds
    abstract fun bindRegistrationPresenter(presenter: RegistrationPresenter): IRegistrationPresenter
}