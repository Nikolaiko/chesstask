package com.otus.homework.onboarding.reducers

import com.example.core.model.user.UserProfile
import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.model.responses.UserDataResponse
import com.otus.homework.network.model.user.Tokens
import com.otus.homework.onboarding.MIN_EMAIL_LENGTH
import com.otus.homework.onboarding.RxJavaRule
import com.otus.homework.onboarding.model.RegistrationState
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.onboarding.reducer.RegistrationReducer
import com.otus.homework.storage.implementations.UserDataRepository
import com.otus.homework.storage.interfaces.LoggedUserProvider
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class RegisterReducerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var onBoardingApi: OnBoardingApi

    @Mock
    lateinit var loggedUserProvider: LoggedUserProvider

    private val registrationEnabledState = RegistrationState(registrationButtonEnabled = true)
    private val registrationInProgressState = RegistrationState(
        false,
        registrationButtonEnabled = false,
        loadingActive = true,
        loginTextFieldEnabled = false,
        passwordTextField = false
    )

    @Test
    fun `enter correct format of registration data enables registration button`() {
        val repository = UserDataRepository(onBoardingApi)
        val reducer = RegistrationReducer(repository, loggedUserProvider)

        val registrationStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.credentialsChange(UserProfile("s".repeat(MIN_EMAIL_LENGTH), "password"))
        registrationStateObserver.assertValueCount(1)
        assertEquals(registrationEnabledState, registrationStateObserver.values().first())
    }

    @Test
    fun `registration with existed login and password trigger news with error message`() {
        Mockito.`when`(onBoardingApi.register(UserData()))
            .thenReturn(Single.error<UserDataResponse>(Exception("Already exists")).toObservable())

        val repository = UserDataRepository(onBoardingApi)
        val reducer = RegistrationReducer(repository, loggedUserProvider)

        val registrationNewsObserver = reducer.updateNews
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.tryToRegister()
        registrationNewsObserver.assertValueCount(1)
        assertEquals(NewsMessageId.EXCEPTION_REGISTRATION_REQUEST, registrationNewsObserver.values().first().id)
    }

    @Test
    fun `registration trigger state change`() {
        Mockito.`when`(onBoardingApi.register(UserData()))
            .thenReturn(Single.error<UserDataResponse>(Exception("User not found")).toObservable())

        val repository = UserDataRepository(onBoardingApi)
        val reducer = RegistrationReducer(repository, loggedUserProvider)

        val registrationStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.tryToRegister()
        registrationStateObserver.assertValueCount(2)
        assertEquals(registrationEnabledState, registrationStateObserver.values()[0])
        assertEquals(registrationInProgressState, registrationStateObserver.values()[1])
    }

    @Test
    fun `successful registration trigger navigation to main screen`() {
        Mockito.`when`(onBoardingApi.register(UserData()))
            .thenReturn(
                Single.just(UserDataResponse(200, "success", Tokens("")))
                    .toObservable())

        val repository = UserDataRepository(onBoardingApi)
        val reducer = RegistrationReducer(repository, loggedUserProvider)

        val registerDestinationObserver = reducer.updateDestination
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.tryToRegister()
        registerDestinationObserver.assertValueCount(1)
        assertEquals(OnBoardingScreens.MAIN_SCREEN, registerDestinationObserver.values().first())
    }

    @Test
    fun `back button trigger navigation to login screen`() {
        val repository = UserDataRepository(onBoardingApi)
        val reducer = RegistrationReducer(repository, loggedUserProvider)

        val registrationDestinationObserver = reducer.updateDestination
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.goToPreviousScreen()
        registrationDestinationObserver.assertValueCount(1)
        assertEquals(OnBoardingScreens.LOGIN_SCREEN, registrationDestinationObserver.values().first())
    }
}