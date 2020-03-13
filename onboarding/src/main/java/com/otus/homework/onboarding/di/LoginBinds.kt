package com.otus.homework.onboarding.di

import com.otus.homework.onboarding.presenters.ILoginPresenter
import com.otus.homework.onboarding.presenters.IRegistrationPresenter
import com.otus.homework.onboarding.presenters.LoginPresenter
import com.otus.homework.onboarding.presenters.RegistrationPresenter
import com.otus.homework.onboarding.reducer.ILoginReducer
import com.otus.homework.onboarding.reducer.IRegistrationReducer
import com.otus.homework.onboarding.reducer.LoginReducer
import com.otus.homework.onboarding.reducer.RegistrationReducer
import dagger.Binds
import dagger.Module

@Module
abstract class LoginBinds {
    @Binds
    abstract fun bindLoginReducer(reducer: LoginReducer):ILoginReducer

    @Binds
    abstract fun bindLoginPresenter(presenter: LoginPresenter):ILoginPresenter
}
