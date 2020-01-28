package com.otus.homework.onboarding.reducer

import com.otus.homework.onboarding.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.RegistrationState
import io.reactivex.subjects.PublishSubject

interface IRegistrationReducer {
    val updateState: PublishSubject<RegistrationState>
    val updateNews: PublishSubject<News>
    val updateDestination: PublishSubject<AppScreens>

    fun credentialsChange(userData:UserShortData): RegistrationState
    fun tryToRegister(): RegistrationState
    fun goToPreviousScreen()
    fun clearDisposables()
}