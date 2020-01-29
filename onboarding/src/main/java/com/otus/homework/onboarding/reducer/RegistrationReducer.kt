package com.otus.homework.onboarding.reducer

import com.example.core_api.model.UserProfile
import com.example.core_api.network.OnBoardingApi
import com.example.core_api.utils.LoggedUserProvider
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.RegistrationState
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.model.user.UserShortData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RegistrationReducer @Inject constructor(
    private val backend: OnBoardingApi,
    private val userData: LoggedUserProvider) : IRegistrationReducer {

    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateState: PublishSubject<RegistrationState> = PublishSubject.create()
    override val updateNews: PublishSubject<News> = PublishSubject.create()
    override val updateDestination: PublishSubject<OnBoardingScreens> = PublishSubject.create()

    private var currentUserData = UserShortData()
    private var currentState = RegistrationState()
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData:UserShortData): RegistrationState {
        currentUserData = userData
        currentState = currentState.copy(registrationButtonEnabled = (currentUserData.email.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        return currentState
    }

    override fun tryToRegister(): RegistrationState {
        disposeBag.add(backend.register(currentUserData)
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
        updateDestination.onNext(OnBoardingScreens.LOGIN_SCREEN)
    }

    private fun tryToLoginAfterRegistration(newUserData:UserShortData) {
        disposeBag.add(backend.login(UserShortData(newUserData.email, newUserData.password))
            .subscribeOn(Schedulers.io())
            .subscribe( {
                when (it.isSuccessful) {
                    true -> {
                        if (it.body() != null) {
                            userData.setLoggedUser(UserProfile(newUserData.email))
                            updateDestination.onNext(OnBoardingScreens.MAIN_SCREEN)
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
            }))
    }
}