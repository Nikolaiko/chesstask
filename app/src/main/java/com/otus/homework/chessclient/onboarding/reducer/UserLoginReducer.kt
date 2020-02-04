package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.UserApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class UserLoginReducer(private val backend:UserApi) : LoginReducer {
    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateDestination:PublishSubject<AppScreens>  = PublishSubject.create()
    override val updateState:PublishSubject<LoginState> = PublishSubject.create()
    override val updateNews:PublishSubject<News> = PublishSubject.create()

    private var currentUserData = UserShortData()
    private var currentState = LoginState()
    private val disposeBag:CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserShortData) {
        currentUserData = userData
        currentState = currentState.copy(loginButtonEnabled = (currentUserData.email.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        updateState.onNext(currentState)
    }

    override fun tryToLogin() {
        backend.login(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> updateDestination.onNext(AppScreens.MAIN_SCREEN)
                    false -> {
                        currentState = LoginState()
                        updateNews.onNext(News(NewsMessageId.REQUEST_STATUS_ERROR, it.code().toString()))
                    }
                }
                updateState.onNext(currentState)
            }, {
                updateNews.onNext(News(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
            })
            .addTo(disposeBag)

        currentState = LoginState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        updateState.onNext(currentState)
    }

    override fun register() {
        updateDestination.onNext(AppScreens.REGISTER_SCREEN)
        updateState.onNext(currentState)
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }
}