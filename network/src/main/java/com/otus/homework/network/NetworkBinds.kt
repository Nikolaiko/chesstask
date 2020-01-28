package com.otus.homework.network

import com.example.core_api.network.OnBoardingApi
import com.example.core_api.network.RetrofitBuilder
import dagger.Binds
import dagger.Module

@Module
interface NetworkBinds {
    @Binds
    fun bindOnBoardingApi(api: com.otus.homework.network.OnBoardingApiImpl): OnBoardingApi

    @Binds
    fun bindRetrofit(retrofit: com.otus.homework.network.RetrofitBuilderImpl): RetrofitBuilder
}