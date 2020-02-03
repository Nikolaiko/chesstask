package com.otus.homework.onboarding.reducer

import com.example.core_api.model.UserProfile
import com.example.core_api.utils.LoggedUserProvider
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.storage.UserDataRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginReducer @Inject constructor(
    private val backend: UserDataRepository,
    private val userData : LoggedUserProvider) : ILoginReducer {

    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateDestination:PublishSubject<OnBoardingScreens>  = PublishSubject.create()
    override val updateState:PublishSubject<LoginState> = PublishSubject.create()
    override val updateNews:PublishSubject<News> = PublishSubject.create()

    private var currentUserData = UserProfile("", "")
    private var currentState = LoginState()
    private val disposeBag:CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserProfile):LoginState {
        currentUserData = userData
        currentState = currentState.copy(loginButtonEnabled = (currentUserData.username.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        return currentState
    }

    override fun tryToLogin():LoginState {
        disposeBag.add(backend.login(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                userData.setLoggedUser(it)
                updateState.onNext(currentState)
                updateDestination.onNext(OnBoardingScreens.MAIN_SCREEN)
            }, {
                currentState = LoginState()
                updateNews.onNext(News(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
                updateState.onNext(currentState)
            }))

        currentState = LoginState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        return currentState
    }

    override fun register(): LoginState {
        updateDestination.onNext(OnBoardingScreens.REGISTER_SCREEN)
        return currentState
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }
}