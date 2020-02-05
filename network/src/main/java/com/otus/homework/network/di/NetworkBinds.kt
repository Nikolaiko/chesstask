package com.otus.homework.network.di

import com.otus.homework.network.implementations.ChessTasksApiImpl
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.interfaces.RetrofitBuilder
import com.otus.homework.network.implementations.OnBoardingApiImpl
import com.otus.homework.network.implementations.RetrofitBuilderImpl
import com.otus.homework.network.interfaces.ChessTasksApi
import dagger.Binds
import dagger.Module

@Module
interface NetworkBinds {
    @Binds
    fun bindOnBoardingApi(api: OnBoardingApiImpl): OnBoardingApi

    @Binds
    fun bindChessTasksApi(api: ChessTasksApiImpl): ChessTasksApi

    @Binds
    fun bindRetrofit(retrofit: RetrofitBuilderImpl): RetrofitBuilder
}