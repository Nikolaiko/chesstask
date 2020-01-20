package com.otus.homework.chessclient.core.di

import com.otus.homework.network.OnBoardingApi
import com.otus.homework.network.RetrofitBuilder
import com.otus.homework.network.interfaces.IOnBoardingApi
import com.otus.homework.network.interfaces.IRetrofitBuilder
import dagger.Binds
import dagger.Module

@Module
interface CoreBinds {
    @Binds
    fun bindOnBoardingApi(api: OnBoardingApi): IOnBoardingApi

    @Binds
    fun bindRetrofit(retrofit: RetrofitBuilder): IRetrofitBuilder
}