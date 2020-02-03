package com.otus.homework.onboarding

import android.content.Context
import com.example.core.mediator.OnBoardingMediator
import javax.inject.Inject

class OnBoardingMediatorImpl @Inject constructor() : OnBoardingMediator {
    override fun createOnBoardingActivity(context: Context) {
        OnBoardingActivity.startOnBoardingActivity(context)
    }
}