package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import io.reactivex.subjects.PublishSubject

interface LoginReducer {
    val updateState:PublishSubject<LoginState>
    val updateNews:PublishSubject<News>
    val updateDestination:PublishSubject<AppScreens>

    fun credentialsChange(userData: UserShortData)
    fun tryToLogin()
    fun register()
    fun clearDisposables()
}