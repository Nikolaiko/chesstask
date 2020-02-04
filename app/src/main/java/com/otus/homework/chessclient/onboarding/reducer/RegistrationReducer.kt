package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.RegistrationState
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface RegistrationReducer {
    val updateState: Observable<RegistrationState>
    val updateNews: Observable<News>
    val updateDestination: Observable<AppScreens>

    fun credentialsChange(userData:UserShortData)
    fun tryToRegister()
    fun goToPreviousScreen()
    fun clearDisposables()
}