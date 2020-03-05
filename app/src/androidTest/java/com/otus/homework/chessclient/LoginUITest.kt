package com.otus.homework.chessclient

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.otus.homework.chessclient.dispatchers.FailedLoginDispatcher
import com.otus.homework.chessclient.dispatchers.SuccessLoginDispatcher
import com.otus.homework.di.AppModule
import com.otus.homework.di.AppModule.Companion.PREFS_NAME
import com.otus.homework.main.MainActivity
import com.otus.homework.onboarding.reducer.LoginReducer
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class LoginUITest {

    private val mockServer = MockWebServer()

    @get:Rule
    var activityRule: FreshLoginTestRule<MainActivity> = FreshLoginTestRule<MainActivity>(
        MainActivity::class.java, false, false
    )

    @Before
    fun setup() {
        mockServer.start(8080)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun appFromStart_emptyTextFields_LoginButtonDisabled() {
        activityRule.launchActivity(null)
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun appFromStart_enterOnlyLogin_LoginButtonDisabled() {
        activityRule.launchActivity(null)
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("someLogin"))
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun appFromStart_enterPasswordAndShortLogin_LoginButtonDisabled() {
        activityRule.launchActivity(null)
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("s".repeat(LoginReducer.MIN_EMAIL_LENGTH - 1)), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("somePassword"))
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun appFromStart_enterPasswordAndLogin_LoginButtonEnabled() {
        activityRule.launchActivity(null)
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("s".repeat(LoginReducer.MIN_EMAIL_LENGTH)), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("somePassword"))
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(isEnabled()))
    }

    @Test
    fun appFromStart_enterPasswordAndLogin_LoginFails() {
        mockServer.dispatcher = FailedLoginDispatcher()
        activityRule.launchActivity(null)

        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("u@mail.ru"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).perform(click())
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).check(matches(isEnabled()))
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).check(matches(isEnabled()))
    }

    @Test
    fun appFromStart_enterPasswordAndLogin_LoginSuccess() {
        mockServer.dispatcher = SuccessLoginDispatcher()
        activityRule.launchActivity(null)

        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("u@mail.ru"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).perform(click())
        onView(withId(com.otus.homework.taskslist.R.id.tasksList)).check(matches(isEnabled()))
    }
}