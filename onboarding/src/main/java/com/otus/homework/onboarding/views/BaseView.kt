package com.otus.homework.onboarding.views

import com.otus.homework.onboarding.model.OnBoardingNews
import com.otus.homework.onboarding.model.enums.OnBoardingScreens

interface BaseView {
    fun displayMessage(newsMessage: OnBoardingNews)
    fun navigateTo(destination:OnBoardingScreens)
}
