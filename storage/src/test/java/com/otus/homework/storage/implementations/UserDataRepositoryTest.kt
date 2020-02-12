package com.otus.homework.storage.implementations

import com.otus.homework.storage.RxJavaRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserDataRepositoryTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Test
    fun login() {

    }

    @Test
    fun loginNullTest() {

    }

    @Test
    fun loginWrongStatusTest() {

    }


    @Test
    fun registerNewUser() {
    }
}