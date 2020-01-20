package com.otus.homework.chessclient.core.di

import com.otus.homework.chessclient.onboarding.di.LoginBinds
import com.otus.homework.chessclient.onboarding.di.LoginComponent
import com.otus.homework.chessclient.onboarding.di.RegistrationBinds
import com.otus.homework.chessclient.onboarding.di.RegistrationComponent
import com.otus.homework.network.interfaces.IOnBoardingApi
import com.otus.homework.network.interfaces.IRetrofitBuilder
import dagger.Component
import javax.inject.Scope

@AppScope
@Component(modules = [CoreBinds::class])
interface CoreComponent {
    fun provideOnBoardingApi():IOnBoardingApi
    fun provideRetrofit():IRetrofitBuilder
}