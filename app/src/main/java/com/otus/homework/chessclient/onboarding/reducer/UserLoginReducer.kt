package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.UserApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class UserLoginReducer(private val backend:UserApi) : LoginReducer {
    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    private val _updateDestination: PublishSubject<AppScreens> = PublishSubject.create()
    override val updateDestination: Observable<AppScreens>
        get() = _updateDestination

    private val _updateState: PublishSubject<LoginState> = PublishSubject.create()
    override val updateState: Observable<LoginState>
        get() = _updateState

    private val _updateNews: PublishSubject<News> = PublishSubject.create()
    override val updateNews: Observable<News>
        get() = _updateNews

    private var currentUserData = UserShortData()
    private var currentState = LoginState()
    private val disposeBag:CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserShortData) {
        currentUserData = userData
        currentState = currentState.copy(loginButtonEnabled = (currentUserData.email.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        _updateState.onNext(currentState)
    }

    override fun tryToLogin() {
        backend.login(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                _updateDestination.onNext(AppScreens.MAIN_SCREEN)
            }, {
                _updateNews.onNext(News(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
                currentState = LoginState()
                _updateState.onNext(currentState)
            })
            .addTo(disposeBag)

        currentState = LoginState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        _updateState.onNext(currentState)
    }

    override fun register() {
        _updateDestination.onNext(AppScreens.REGISTER_SCREEN)
        _updateState.onNext(currentState)
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }
}