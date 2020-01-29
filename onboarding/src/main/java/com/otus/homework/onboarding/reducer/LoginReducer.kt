package com.otus.homework.onboarding.reducer

import com.example.core_api.model.UserProfile
import com.example.core_api.network.OnBoardingApi
import com.example.core_api.utils.LoggedUserProvider
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.enums.NewsMessageId
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginReducer @Inject constructor(
    private val backend: OnBoardingApi,
    private val userData : LoggedUserProvider) : ILoginReducer {

    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateDestination:PublishSubject<OnBoardingScreens>  = PublishSubject.create()
    override val updateState:PublishSubject<LoginState> = PublishSubject.create()
    override val updateNews:PublishSubject<News> = PublishSubject.create()

    private var currentUserData = UserShortData()
    private var currentState = LoginState()
    private val disposeBag:CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserShortData):LoginState {
        currentUserData = userData
        currentState = currentState.copy(loginButtonEnabled = (currentUserData.email.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        return currentState
    }

    override fun tryToLogin():LoginState {
        disposeBag.add(backend.login(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> {
                        userData.setLoggedUser(UserProfile(currentUserData.email))
                        updateDestination.onNext(OnBoardingScreens.MAIN_SCREEN)
                    }
                    false -> {
                        currentState = LoginState()
                        updateNews.onNext(News(NewsMessageId.REQUEST_STATUS_ERROR, it.code().toString()))
                    }
                }
                updateState.onNext(currentState)
            }, {
                updateNews.onNext(News(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
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