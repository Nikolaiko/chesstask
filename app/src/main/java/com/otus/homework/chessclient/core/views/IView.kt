package com.otus.homework.chessclient.core.views

import com.otus.homework.model.enums.AppScreens

interface IView {
    fun displayMessage(message:String)
    fun navigateTo(destination:AppScreens)
}