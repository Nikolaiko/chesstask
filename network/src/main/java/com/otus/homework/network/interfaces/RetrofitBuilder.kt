package com.otus.homework.network.interfaces

import com.otus.homework.network.interfaces.OnBoardingService

interface RetrofitBuilder {
    fun buildOnBoardingService(): OnBoardingService
}