package com.otus.homework.chessclient.onboarding.reducer

import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.model.RegistrationState
import com.otus.homework.model.enums.AppScreens
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.IOnBoardingApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RegistrationReducer(private val backend:IOnBoardingApi) : IRegistrationReducer {
    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateState: PublishSubject<RegistrationState> = PublishSubject.create()
    override val updateNews: PublishSubject<String> = PublishSubject.create()
    override val updateDestination: PublishSubject<AppScreens> = PublishSubject.create()

    private var email:String = ""
    private var password:String = ""
    private var currentState = RegistrationState()
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(values: List<String>): RegistrationState {
        email = values[0]
        password = values[1]
        currentState = currentState.copy(registrationButtonEnabled = (email.length > MIN_EMAIL_LENGTH && password.isNotEmpty()))
        return currentState
    }

    override fun tryToRegister(): RegistrationState {
        disposeBag.add(backend.register(UserShortData(email, password))
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> {
                        if (it.body() != null) {
                            tryToLoginAfterRegistration(it.body()!!)
                        } else {
                            currentState = RegistrationState()
                            updateNews.onNext("Unknown error during registration request : body is null")
                            updateState.onNext(currentState)
                        }
                    }
                    false -> {
                        currentState = RegistrationState()
                        updateNews.onNext("Error with status : ${it.code()}")
                        updateState.onNext(currentState)
                    }
                }
            }, {
                updateNews.onNext("Exception during registration request : ${it.localizedMessage}")
            }))

        currentState = RegistrationState(false,
            registrationButtonEnabled = false,
            loadingActive = true,
            loginTextFieldEnabled = false,
            passwordTextField = false
        )
        return currentState
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }

    override fun goToPreviousScreen() {
        updateDestination.onNext(AppScreens.LOGIN_SCREEN)
    }

    private fun tryToLoginAfterRegistration(newUserData:UserShortData) {
        disposeBag.add(backend.login(UserShortData(newUserData.email, newUserData.password))
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> {
                        if (it.body() != null) {
                            updateDestination.onNext(AppScreens.MAIN_SCREEN)
                        } else {
                            currentState = RegistrationState()
                            updateNews.onNext("Unknown error during login after registration request : body is null")
                            updateState.onNext(currentState)
                        }
                    }
                    false -> {
                        currentState = RegistrationState()
                        updateNews.onNext("Registration Successful, but login failed with error with status : ${it.code()}")
                        updateState.onNext(currentState)
                    }
                }
            }, {
                updateNews.onNext("Exception during registration request : ${it.localizedMessage}")
            }))
    }
}