package com.otus.homework.onboarding.reducers

import com.example.core.model.user.UserProfile
import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.model.responses.UserDataResponse
import com.otus.homework.network.model.user.Tokens
import com.otus.homework.onboarding.MIN_EMAIL_LENGTH
import com.otus.homework.onboarding.RxJavaRule
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import com.otus.homework.onboarding.reducer.LoginReducer
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

class LoginReducerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var onBoardingApi: OnBoardingApi

    @Mock
    lateinit var loggedUserProvider: LoggedUserProvider

    private val loginEnabledState = LoginState(loginButtonEnabled = true)
    private val loginInProgressState = LoginState(false,
        registrationButtonEnabled = false,
        loadingActive = true,
        loginTextFieldEnabled = false,
        passwordTextField = false
    )

    @Test
    fun `enter correct format of login data enables login button`() {
        val repository = UserDataRepository(onBoardingApi)
        val reducer = LoginReducer(repository, loggedUserProvider)

        val loginStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.credentialsChange(UserProfile("s".repeat(MIN_EMAIL_LENGTH), "password"))
        loginStateObserver.assertValueCount(1)
        assertEquals(loginEnabledState, loginStateObserver.values().first())
    }

    @Test
    fun `login with wrong login and password trigger news with error message`() {
        Mockito.`when`(onBoardingApi.login(UserData()))
            .thenReturn(Single.error<UserDataResponse>(Exception("User not found")).toObservable())

        val repository = UserDataRepository(onBoardingApi)
        val reducer = LoginReducer(repository, loggedUserProvider)

        val loginNewsObserver = reducer.updateNews
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.tryToLogin()
        loginNewsObserver.assertValueCount(1)
        assertEquals(NewsMessageId.EXCEPTION_LOGIN_REQUEST, loginNewsObserver.values().first().id)
    }

    @Test
    fun `login trigger state change`() {
        Mockito.`when`(onBoardingApi.login(UserData()))
            .thenReturn(Single.error<UserDataResponse>(Exception("User not found")).toObservable())

        val repository = UserDataRepository(onBoardingApi)
        val reducer = LoginReducer(repository, loggedUserProvider)

        val loginStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.tryToLogin()
        loginStateObserver.assertValueCount(2)
        assertEquals(loginInProgressState, loginStateObserver.values()[1])
        assertEquals(loginEnabledState, loginStateObserver.values()[0])
    }

    @Test
    fun `successful login trigger navigation to main screen`() {
        Mockito.`when`(onBoardingApi.login(UserData()))
            .thenReturn(
                Single.just(UserDataResponse(200, "success", Tokens("")))
                    .toObservable())

        val repository = UserDataRepository(onBoardingApi)
        val reducer = LoginReducer(repository, loggedUserProvider)

        val loginDestinationObserver = reducer.updateDestination
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.tryToLogin()
        loginDestinationObserver.assertValueCount(1)
        assertEquals(OnBoardingScreens.MAIN_SCREEN, loginDestinationObserver.values().first())
    }

    @Test
    fun `register button trigger navigation to register screen`() {
        val repository = UserDataRepository(onBoardingApi)
        val reducer = LoginReducer(repository, loggedUserProvider)

        val loginDestinationObserver = reducer.updateDestination
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.register()
        loginDestinationObserver.assertValueCount(1)
        assertEquals(OnBoardingScreens.REGISTER_SCREEN, loginDestinationObserver.values().first())
    }
}