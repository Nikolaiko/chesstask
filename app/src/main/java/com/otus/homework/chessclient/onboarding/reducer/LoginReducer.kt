package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
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
    override val updateNews:PublishSubject<String> = PublishSubject.create()

    private var email:String = ""
    private var password:String = ""
    private var currentState = LoginState()
    private val disposeBag:CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(values:List<String>):LoginState {
        email = values[0]
        password = values[1]
        currentState = currentState.copy(loginButtonEnabled = (email.length > MIN_EMAIL_LENGTH && password.isNotEmpty()))
        return currentState
    }

    override fun tryToLogin():LoginState {
        disposeBag.add(backend.login(UserShortData(email, password))
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> updateDestination.onNext(AppScreens.MAIN_SCREEN)
                    false -> {
                        currentState = LoginState()
                        updateNews.onNext("Error with status : ${it.code()}")
                    }
                }
                updateState.onNext(currentState)
            }, {
                updateNews.onNext("Exception during login request : ${it.localizedMessage}")
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