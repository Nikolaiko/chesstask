package com.example.core.mediator

interface MediatorsProvider {
    fun provideOnBoardingMediator(): OnBoardingMediator
    fun provideTasksListMediator(): TasksListMediator
}