package com.otus.homework.onboarding.reducer

import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import io.reactivex.subjects.PublishSubject

interface ILoginReducer {
    val updateState:PublishSubject<LoginState>
    val updateNews:PublishSubject<News>
    val updateDestination:PublishSubject<AppScreens>

    fun credentialsChange(userData: UserShortData):LoginState
    fun tryToLogin():LoginState
    fun register():LoginState
    fun clearDisposables()
}