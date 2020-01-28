package com.otus.homework.onboarding.views

import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.enums.AppScreens

interface IView {
    fun displayMessage(newsMessage: News)
    fun navigateTo(destination:AppScreens)
}