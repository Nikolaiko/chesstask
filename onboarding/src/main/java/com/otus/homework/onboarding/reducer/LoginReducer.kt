package com.otus.homework.onboarding.reducer

import com.example.core.model.user.UserProfile
import com.otus.homework.onboarding.isUserDataCorrect
import com.otus.homework.storage.interfaces.LoggedUserProvider
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.OnBoardingNews
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.storage.implementations.UserDataRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginReducer @Inject constructor(
    private val backend: UserDataRepository,
    private val userData : LoggedUserProvider
) : UserLoginReducer {

    override val updateDestination:PublishSubject<OnBoardingScreens>  = PublishSubject.create()
    override val updateState:PublishSubject<LoginState> = PublishSubject.create()
    override val updateNews:PublishSubject<OnBoardingNews> = PublishSubject.create()

    private var currentUserData = UserProfile("", "")
    private var currentState = LoginState()
    private val disposeBag:CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData: UserProfile) {
        currentUserData = userData
        currentState = currentState.copy(loginButtonEnabled = isUserDataCorrect(currentUserData))
        updateState.onNext(currentState)
    }

    override fun tryToLogin() {
        disposeBag.add(backend.login(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                updateState.onNext(currentState)
                userData.setLoggedUser(currentUserData)
                userData.setLoggedUserTokens(it)
                updateDestination.onNext(OnBoardingScreens.MAIN_SCREEN)
            }, {
                currentState = LoginState(loginButtonEnabled = true)
                updateNews.onNext(OnBoardingNews(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
                updateState.onNext(currentState)
            }))

        currentState = LoginState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        updateState.onNext(currentState)
    }

    override fun register() {
        updateDestination.onNext(OnBoardingScreens.REGISTER_SCREEN)
        updateState.onNext(currentState)
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }
}
