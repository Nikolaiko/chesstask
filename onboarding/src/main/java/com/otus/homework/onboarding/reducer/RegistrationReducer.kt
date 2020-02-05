package com.otus.homework.onboarding.reducer

import com.example.core.model.user.UserProfile
import com.otus.homework.storage.interfaces.LoggedUserProvider
import com.otus.homework.onboarding.model.OnBoardingNews
import com.otus.homework.onboarding.model.RegistrationState
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.storage.implementations.UserDataRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RegistrationReducer @Inject constructor(
    private val backend: UserDataRepository,
    private val userData: LoggedUserProvider
) : IRegistrationReducer {

    companion object{
        private const val MIN_EMAIL_LENGTH:Int = 3
    }

    override val updateState: PublishSubject<RegistrationState> = PublishSubject.create()
    override val updateNews: PublishSubject<OnBoardingNews> = PublishSubject.create()
    override val updateDestination: PublishSubject<OnBoardingScreens> = PublishSubject.create()

    private var currentUserData = UserProfile("", "")
    private var currentState = RegistrationState()
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun credentialsChange(userData: UserProfile): RegistrationState {
        currentUserData = userData
        currentState = currentState.copy(registrationButtonEnabled = (currentUserData.username.length > MIN_EMAIL_LENGTH && currentUserData.password.isNotEmpty()))
        return currentState
    }

    override fun tryToRegister(): RegistrationState {
        disposeBag.add(backend.registerNewUser(currentUserData)
            .subscribeOn(Schedulers.io())
            .subscribe( {
                userData.setLoggedUser(currentUserData)
                userData.setLoggedUserTokens(it)
                updateDestination.onNext(OnBoardingScreens.MAIN_SCREEN)
            }, {
                currentState = RegistrationState()
                updateNews.onNext(OnBoardingNews(NewsMessageId.EXCEPTION_REGISTRATION_REQUEST, it.localizedMessage ?: ""))
                updateState.onNext(currentState)
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
}