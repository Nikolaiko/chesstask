package com.otus.homework.network.di

import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.interfaces.RetrofitBuilder
import com.otus.homework.network.implementations.OnBoardingApiImpl
import com.otus.homework.network.implementations.RetrofitBuilderImpl
import dagger.Binds
import dagger.Module

@Module
interface NetworkBinds {
    @Binds
    fun bindOnBoardingApi(api: OnBoardingApiImpl): OnBoardingApi

    @Binds
    fun bindRetrofit(retrofit: RetrofitBuilderImpl): RetrofitBuilder
}