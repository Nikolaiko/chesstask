package com.otus.homework.onboarding.presenters

import com.otus.homework.onboarding.RxJavaRule
import com.otus.homework.onboarding.reducer.UserRegistrationReducer
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class RegistrationPresenterTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var reducer: UserRegistrationReducer

    @Test
    fun `presenter call clear disposables on connected reducer when detached`() {
        val registerPresenter = RegistrationPresenter(reducer)
        registerPresenter.detachView()

        Mockito.verify(reducer, times(1)).clearDisposables()
    }
}