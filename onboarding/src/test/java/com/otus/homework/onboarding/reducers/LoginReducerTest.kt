package com.otus.homework.onboarding.reducers

import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.onboarding.RxJavaRule
import com.otus.homework.onboarding.doplers.OnBoardingApiTestImpl
import com.otus.homework.storage.implementations.UserDataRepository
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class LoginReducerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var onBoardingApi: OnBoardingApi

    val repository = UserDataRepository(onBoardingApi)

    @Test
    fun `login error with wrong login and password`() {

    }

}