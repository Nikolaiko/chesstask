package com.otus.homework.chessclient.onboarding.di

import com.otus.homework.chessclient.onboarding.presenters.ILoginPresenter
import com.otus.homework.chessclient.onboarding.presenters.IRegistrationPresenter
import com.otus.homework.chessclient.onboarding.presenters.LoginPresenter
import com.otus.homework.chessclient.onboarding.presenters.RegistrationPresenter
import com.otus.homework.chessclient.onboarding.reducer.ILoginReducer
import com.otus.homework.chessclient.onboarding.reducer.IRegistrationReducer
import com.otus.homework.chessclient.onboarding.reducer.LoginReducer
import com.otus.homework.chessclient.onboarding.reducer.RegistrationReducer
import com.otus.homework.network.OnBoardingApi
import com.otus.homework.network.RetrofitBuilder
import com.otus.homework.network.interfaces.IOnBoardingApi
import com.otus.homework.network.interfaces.IRetrofitBuilder
import dagger.Binds
import dagger.Module

@Module
abstract class LoginBinds {
    @Binds
    abstract fun bindLoginReducer(reducer: LoginReducer):ILoginReducer

    @Binds
    abstract fun bindLoginPresenter(presenter: LoginPresenter):ILoginPresenter
}