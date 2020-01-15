package com.otus.homework.chessclient.core.views

import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.model.enums.AppScreens

interface IView {
    fun displayMessage(newsMessage: News)
    fun navigateTo(destination:AppScreens)
}