package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.RegistrationState
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import io.reactivex.subjects.PublishSubject

interface RegistrationReducer {
    val updateState: PublishSubject<RegistrationState>
    val updateNews: PublishSubject<News>
    val updateDestination: PublishSubject<AppScreens>

    fun credentialsChange(userData:UserShortData)
    fun tryToRegister()
    fun goToPreviousScreen()
    fun clearDisposables()
}