package com.otus.homework.onboarding.di

import com.otus.homework.onboarding.presenters.UserLoginPresenter
import com.otus.homework.onboarding.presenters.LoginPresenter
import com.otus.homework.onboarding.reducer.UserLoginReducer
import com.otus.homework.onboarding.reducer.LoginReducer
import dagger.Binds
import dagger.Module

@Module
abstract class LoginBinds {
    @Binds
    abstract fun bindLoginReducer(reducer: LoginReducer):UserLoginReducer

    @Binds
    abstract fun bindLoginPresenter(presenter: LoginPresenter):UserLoginPresenter
}
