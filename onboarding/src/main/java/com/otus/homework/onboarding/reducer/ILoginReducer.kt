package com.otus.homework.onboarding.reducer

import com.example.core.model.user.UserProfile
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.OnBoardingNews
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import io.reactivex.subjects.PublishSubject

interface ILoginReducer {
    val updateState: PublishSubject<LoginState>
    val updateNews: PublishSubject<OnBoardingNews>
    val updateDestination: PublishSubject<OnBoardingScreens>

    fun credentialsChange(userData: UserProfile)
    fun tryToLogin()
    fun register()
    fun clearDisposables()
}
