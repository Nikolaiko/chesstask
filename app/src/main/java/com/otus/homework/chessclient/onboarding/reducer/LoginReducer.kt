package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.model.News
import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.IOnBoardingApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class LoginReducer(private val backend:IOnBoardingApi) : ILoginReducer {
    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateDestination:PublishSubject<AppScreens>  = PublishSubject.create()
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
                    true -> updateDestination.onNext(AppScreens.MAIN_SCREEN)
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
        updateDestination.onNext(AppScreens.REGISTER_SCREEN)
        return currentState
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }
}