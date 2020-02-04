package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.RegistrationState
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.UserApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class UserRegistrationReducer(private val backend:UserApi) : RegistrationReducer {
    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateState: PublishSubject<RegistrationState> = PublishSubject.create()
    override val updateNews: PublishSubject<News> = PublishSubject.create()
    override val updateDestination: PublishSubject<AppScreens> = PublishSubject.create()

    private var currentUserData = UserShortData()
    private var currentState = RegistrationState()
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserShortData) {
        currentUserData = userData
        currentState = currentState.copy(registrationButtonEnabled = (currentUserData.email.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        updateState.onNext(currentState)
    }

    override fun tryToRegister() {
        backend.register(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> {
                        if (it.body() != null) {
                            tryToLoginAfterRegistration(it.body()!!)
                        } else {
                            currentState = RegistrationState()
                            updateNews.onNext(News(NewsMessageId.NULL_BODY_MESSAGE))
                            updateState.onNext(currentState)
                        }
                    }
                    false -> {
                        currentState = RegistrationState()
                        updateNews.onNext(News(NewsMessageId.REQUEST_STATUS_ERROR, it.code().toString()))
                        updateState.onNext(currentState)
                    }
                }
            }, {
                updateNews.onNext(News(NewsMessageId.EXCEPTION_REGISTRATION_REQUEST, it.localizedMessage ?: ""))
            })
            .addTo(disposeBag)

        currentState = RegistrationState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        updateState.onNext(currentState)
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }

    override fun goToPreviousScreen() {
        updateDestination.onNext(AppScreens.LOGIN_SCREEN)
    }

    private fun tryToLoginAfterRegistration(newUserData:UserShortData) {
        backend.login(UserShortData(newUserData.email, newUserData.password))
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> {
                        if (it.body() != null) {
                            updateDestination.onNext(AppScreens.MAIN_SCREEN)
                        } else {
                            currentState = RegistrationState()
                            updateNews.onNext(News(NewsMessageId.NULL_BODY_MESSAGE))
                            updateState.onNext(currentState)
                        }
                    }
                    false -> {
                        currentState = RegistrationState()
                        updateNews.onNext(News(NewsMessageId.REQUEST_STATUS_ERROR, it.code().toString()))
                        updateState.onNext(currentState)
                    }
                }
            }, {
                updateNews.onNext(News(NewsMessageId.EXCEPTION_LOGIN_REQUEST, it.localizedMessage ?: ""))
            })
            .addTo(disposeBag)
    }
}