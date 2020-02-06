package com.otus.homework.di

import com.example.core.mediator.OnBoardingMediator
import com.example.core.mediator.SingleChessTaskMediator
import com.example.core.mediator.TasksListMediator
import com.otus.homework.chesstask.ChessTaskMediator
import com.otus.homework.onboarding.OnBoardingMediatorImpl
import com.otus.homework.taskslist.TasksListMediatorImpl
import dagger.Binds
import dagger.Module

@Module
interface MediatorsBindings {
    @Binds
    fun providesOnBoardingMediator(mediator:OnBoardingMediatorImpl): OnBoardingMediator

    @Binds
    fun providesTasksListMediator(mediator:TasksListMediatorImpl): TasksListMediator

    @Binds
    fun provideChessTaskMediator(mediator:ChessTaskMediator): SingleChessTaskMediator
}