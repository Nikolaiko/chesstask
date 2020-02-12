package com.otus.homework.storage.implementations

import com.example.core.model.user.UserProfile
import com.example.core.model.user.UserTokens
import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.model.responses.UserDataResponse
import com.otus.homework.network.model.user.Tokens
import com.otus.homework.storage.RxJavaRule
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserDataRepositoryTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var apiUser: OnBoardingApi

    private lateinit var repository: UserDataRepository

    private val userTokens = UserTokens("access tokens")
    private val tokens = Tokens("access tokens")
    private val successUserDataResponse = UserDataResponse(200, null, tokens)
    private val nullUserDataResponse = UserDataResponse(200, "Null Data", null)



    @Before
    fun setUp() {
        repository = UserDataRepository(apiUser)
    }

    @Test
    fun login() {
        Mockito.`when`(apiUser.login(UserData("", "")))
            .thenReturn(Single.just(successUserDataResponse).toObservable())

        repository.login(UserProfile("", ""))
            .test()
            .assertResult(userTokens)
    }

    @Test
    fun loginNullTest() {
        Mockito.`when`(apiUser.login(UserData("", "")))
            .thenReturn(Single.just(nullUserDataResponse).toObservable())

        repository.login(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun loginWrongStatusTest() {
        Mockito.`when`(apiUser.login(UserData("", "")))
            .thenReturn(Single.error<UserDataResponse>(Exception("Wrong Status")).toObservable())

        repository.login(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }


    @Test
    fun registerNewUser() {
        Mockito.`when`(apiUser.register(UserData("", "")))
            .thenReturn(Single.just(successUserDataResponse).toObservable())

        repository.registerNewUser(UserProfile("", ""))
            .test()
            .assertResult(userTokens)
    }

    @Test
    fun registerNullTest() {
        Mockito.`when`(apiUser.register(UserData("", "")))
            .thenReturn(Single.just(nullUserDataResponse).toObservable())

        repository.registerNewUser(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun registerWrongStatusTest() {
        Mockito.`when`(apiUser.register(UserData("", "")))
            .thenReturn(Single.error<UserDataResponse>(Exception("Wrong Status")).toObservable())

        repository.registerNewUser(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }
}