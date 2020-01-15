package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.model.enums.AppScreens
import io.reactivex.subjects.PublishSubject

interface IRegistrationReducer {
    val updateState: PublishSubject<LoginState>
    val updateNews: PublishSubject<String>
    val updateDestination: PublishSubject<AppScreens>

    fun credentialsChange(values:List<String>): LoginState
    fun tryToRegister(): LoginState
    fun clearDisposables()
}