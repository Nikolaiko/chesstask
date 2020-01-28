package com.otus.homework

import com.example.core_api.mediator.OnBoardingMediator
import com.otus.homework.onboarding.OnBoardingMediatorImpl
import dagger.Binds
import dagger.Module

@Module
interface MediatorsBindings {
    @Binds
    fun providesOnBoardingMediator(mediator:OnBoardingMediatorImpl):OnBoardingMediator
}