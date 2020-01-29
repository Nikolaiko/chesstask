package com.example.core_api.mediator

interface MediatorsProvider {
    fun provideOnBoardingMediator():OnBoardingMediator
    fun provideTasksListMediator():TasksListMediator
}