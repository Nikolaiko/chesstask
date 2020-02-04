package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.RegistrationState
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserProfile
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.UserApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class UserRegistrationReducer(private val backend:UserApi) : RegistrationReducer {
    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    private val _updateState: PublishSubject<RegistrationState> = PublishSubject.create()
    override val updateState: Observable<RegistrationState>
        get() = _updateState

    private val _updateNews: PublishSubject<News> = PublishSubject.create()
    override val updateNews: Observable<News>
        get() = _updateNews

    private val _updateDestination: PublishSubject<AppScreens> = PublishSubject.create()
    override val updateDestination: Observable<AppScreens>
        get() = _updateDestination

    private var currentUserData = UserShortData()
    private var currentState = RegistrationState()
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserShortData) {
        currentUserData = userData
        currentState = currentState.copy(registrationButtonEnabled = (currentUserData.email.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        _updateState.onNext(currentState)
    }

    override fun tryToRegister() {
        backend.register(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                tryToLoginAfterRegistration(it)
            }, {
                _updateNews.onNext(News(NewsMessageId.EXCEPTION_REGISTRATION_REQUEST, it.localizedMessage ?: ""))
                currentState = RegistrationState()
                _updateState.onNext(currentState)
            })
            .addTo(disposeBag)

        currentState = RegistrationState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        _updateState.onNext(currentState)
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }

    override fun goToPreviousScreen() {
        _updateDestination.onNext(AppScreens.LOGIN_SCREEN)
    }

    private fun tryToLoginAfterRegistration(newUserData:UserProfile) {
        backend.login(UserShortData(newUserData.username, newUserData.password))
            .subscribeOn(Schedulers.io())
            .subscribe( {
                _updateDestination.onNext(AppScreens.MAIN_SCREEN)
            }, {
                _updateNews.onNext(News(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
                currentState = RegistrationState()
                _updateState.onNext(currentState)
            })
            .addTo(disposeBag)
    }
}