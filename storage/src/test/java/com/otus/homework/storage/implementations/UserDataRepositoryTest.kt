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

    @Test
    fun predefinedApiResponse_callLogin_compareResultWithExpected() {
        //GIVEN
        val expectedUserTokens = UserTokens("access tokens")
        val tokensResponse = Tokens("access tokens")
        val successUserDataResponse = UserDataResponse(200, null, tokensResponse)
        val repository = UserDataRepository(apiUser)
        Mockito.`when`(apiUser.login(UserData("", "")))
            .thenReturn(Single.just(successUserDataResponse).toObservable())


        //WHEN AND THEN
        repository.login(UserProfile("", ""))
            .test()
            .assertResult(expectedUserTokens)
    }

    @Test
    fun predefinedNullBodyResponse_callLogin_checkExceptionHappen() {
        //GIVEN
        val nullUserDataResponse = UserDataResponse(200, "Null Data", null)
        val repository = UserDataRepository(apiUser)
        Mockito.`when`(apiUser.login(UserData("", "")))
            .thenReturn(Single.just(nullUserDataResponse).toObservable())

        //WHEN AND THEN
        repository.login(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun apiMockReturningWrongStatus_callLogin_checkExceptionHappen() {
        //GIVEN
        val repository = UserDataRepository(apiUser)
        Mockito.`when`(apiUser.login(UserData("", "")))
            .thenReturn(Single.error<UserDataResponse>(Exception("Wrong Status")).toObservable())

        //WHEN AND THEN
        repository.login(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }


    @Test
    fun predefinedTokensResponse_callRegister_compareResultWithExpectedTokens() {
        //GIVEN
        val expectedUserTokens = UserTokens("access tokens")
        val tokensResponse = Tokens("access tokens")
        val successUserDataResponse = UserDataResponse(200, null, tokensResponse)
        val repository = UserDataRepository(apiUser)

        Mockito.`when`(apiUser.register(UserData("", "")))
            .thenReturn(Single.just(successUserDataResponse).toObservable())

        //WHEN AND THEN
        repository.registerNewUser(UserProfile("", ""))
            .test()
            .assertResult(expectedUserTokens)
    }

    @Test
    fun predefinedNullBodyResponse_callRegister_checkExceptionHappen() {
        //GIVEN
        val nullUserDataResponse = UserDataResponse(200, "Null Data", null)
        val repository = UserDataRepository(apiUser)
        Mockito.`when`(apiUser.register(UserData("", "")))
            .thenReturn(Single.just(nullUserDataResponse).toObservable())

        //WHEN AND THEN
        repository.registerNewUser(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun apiMockReturningWrongStatus_callRegister_checkExceptionHappen() {
        //GIVEN
        val repository = UserDataRepository(apiUser)
        Mockito.`when`(apiUser.register(UserData("", "")))
            .thenReturn(Single.error<UserDataResponse>(Exception("Wrong Status")).toObservable())

        repository.registerNewUser(UserProfile("", ""))
            .test()
            .assertFailure(Exception::class.java)
    }
}