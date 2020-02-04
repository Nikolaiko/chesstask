package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import io.reactivex.Observable

interface LoginReducer {
    val updateState: Observable<LoginState>
    val updateNews: Observable<News>
    val updateDestination: Observable<AppScreens>

    fun credentialsChange(userData: UserShortData)
    fun tryToLogin()
    fun register()
    fun clearDisposables()
}