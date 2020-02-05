package com.otus.homework.network.di

import com.otus.homework.network.interfaces.ChessTasksApi
import com.otus.homework.network.interfaces.OnBoardingApi
import dagger.Component
import dagger.Provides

@Component(
    modules = [NetworkBinds::class]
)
interface NetworkComponent {
    fun provideOnBoardingApi(): OnBoardingApi
    fun provideChessTaskApi(): ChessTasksApi
}