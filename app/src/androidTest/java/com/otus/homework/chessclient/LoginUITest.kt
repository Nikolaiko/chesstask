package com.otus.homework.chessclient

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.otus.homework.chessclient.dispatchers.FailedLoginDispatcher
import com.otus.homework.chessclient.dispatchers.SuccessLoginDispatcher
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
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(
        MainActivity::class.java, false, false
    )

    @Before
    fun setup() {
        mockServer.start(8080)
        activityRule.launchActivity(null)

        try {
            onView(withId(com.otus.homework.onboarding.R.id.registerButton)).check(matches(isDisplayed()))
        } catch (exception: NoMatchingViewException) {
            onView((withId(com.otus.homework.taskslist.R.id.logoutButton))).perform(click())
        }
    }

    @Test
    fun allFieldsMustBeEmptyAtStart() {
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun loginButtonDisabledIfOnlyLoginEntered() {
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("someLogin"))
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun loginButtonDisabledIfLoginValueIsTooShort() {
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("s".repeat(LoginReducer.MIN_EMAIL_LENGTH - 1)), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("somePassword"))
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun loginButtonEnabledIfLoginValueAndPasswordEnteredCorrectly() {
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("s".repeat(LoginReducer.MIN_EMAIL_LENGTH)), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("somePassword"))
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(isEnabled()))
    }

    @Test
    fun loginFailIfWrongLoginValueAndPasswordEntered() {
        mockServer.dispatcher = FailedLoginDispatcher()
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("u@mail.ru"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).perform(click())

        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(isEnabled()))
        onView(withId(com.otus.homework.onboarding.R.id.registerButton)).check(matches(isEnabled()))
    }

    @Test
    fun loginSuccessfulIfLoginValueAndPasswordCorrect() {
        mockServer.dispatcher = SuccessLoginDispatcher()
        onView(withId(com.otus.homework.onboarding.R.id.emailText)).perform(typeText("u@mail.ru"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.passwordText)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).perform(click())
        onView(withId(com.otus.homework.taskslist.R.id.tasksList)).check(matches(isEnabled()))
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }
}