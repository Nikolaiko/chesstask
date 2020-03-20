package com.otus.homework.taskslist.presenters

import com.otus.homework.onboarding.RxJavaRule
import com.otus.homework.onboarding.reducer.UserLoginReducer
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class LoginPresenterTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var reducer: UserLoginReducer

    @Test
    fun `presenter call clear disposables on connected reducer when detached`() {
        val loginPresenter = LoginPresenter(reducer)
        loginPresenter.detachView()

        Mockito.verify(reducer, times(1)).clearDisposables()
    }
}