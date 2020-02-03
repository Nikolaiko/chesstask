package com.otus.homework.onboarding.reducer

import com.example.core.model.UserProfile
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import io.reactivex.subjects.PublishSubject

interface ILoginReducer {
    val updateState:PublishSubject<LoginState>
    val updateNews:PublishSubject<News>
    val updateDestination:PublishSubject<OnBoardingScreens>

    fun credentialsChange(userData: UserProfile):LoginState
    fun tryToLogin():LoginState
    fun register():LoginState
    fun clearDisposables()
}