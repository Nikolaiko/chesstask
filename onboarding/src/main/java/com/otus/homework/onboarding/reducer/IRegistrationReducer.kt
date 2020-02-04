package com.otus.homework.onboarding.reducer

import com.example.core.model.user.UserProfile
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.RegistrationState
import io.reactivex.subjects.PublishSubject

interface IRegistrationReducer {
    val updateState: PublishSubject<RegistrationState>
    val updateNews: PublishSubject<News>
    val updateDestination: PublishSubject<OnBoardingScreens>

    fun credentialsChange(userData: UserProfile): RegistrationState
    fun tryToRegister(): RegistrationState
    fun goToPreviousScreen()
    fun clearDisposables()
}