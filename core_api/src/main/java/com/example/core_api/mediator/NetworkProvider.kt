package com.example.core_api.mediator

import com.example.core_api.network.OnBoardingApi

interface NetworkProvider {
    fun provideOnBoardingApi():OnBoardingApi

}