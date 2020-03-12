package com.otus.homework.chessclient

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.otus.homework.chessclient.dispatchers.NotFoundStatusDispatcher
import com.otus.homework.chessclient.dispatchers.SuccessLoginOrRegisterDispatcher
import com.otus.homework.main.MainActivity
import com.otus.homework.onboarding.MIN_EMAIL_LENGTH
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterUITest {
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
            onView(ViewMatchers.withId(R.id.registerButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (exception: NoMatchingViewException) {
            onView((ViewMatchers.withId(com.otus.homework.taskslist.R.id.logoutButton)))
                .perform(ViewActions.click())
        } finally {
            onView((ViewMatchers.withId(com.otus.homework.onboarding.R.id.registerButton)))
                .perform(ViewActions.click())
        }
    }

    @Test
    fun allFieldsMustBeEmptyAtStart() {
        onView(ViewMatchers.withId(R.id.registerButton)).check(
            ViewAssertions.matches(
                CoreMatchers.not(
                    ViewMatchers.isEnabled()
                )
            )
        )
    }

    @Test
    fun registrationButtonDisabledIfOnlyLoginEntered() {
        onView(ViewMatchers.withId(R.id.emailText)).perform(ViewActions.typeText("someLogin"))
        onView(ViewMatchers.withId(R.id.registerButton)).check(
            ViewAssertions.matches(
                CoreMatchers.not(
                    ViewMatchers.isEnabled()
                )
            )
        )
    }

    @Test
    fun registrationButtonDisabledIfLoginValueIsTooShort() {
        onView(ViewMatchers.withId(R.id.emailText)).perform(
            ViewActions.typeText("s".repeat(MIN_EMAIL_LENGTH - 1)), ViewActions.closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.passwordText)).perform(ViewActions.typeText("somePassword"))
        onView(ViewMatchers.withId(R.id.registerButton)).check(
            ViewAssertions.matches(
                CoreMatchers.not(
                    ViewMatchers.isEnabled()
                )
            )
        )
    }

    @Test
    fun registrationButtonEnabledIfLoginValueAndPasswordEnteredCorrectly() {
        onView(ViewMatchers.withId(R.id.emailText)).perform(
            ViewActions.typeText("s".repeat(MIN_EMAIL_LENGTH)), ViewActions.closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.passwordText)).perform(ViewActions.typeText("somePassword"))
        onView(ViewMatchers.withId(R.id.registerButton)).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }

    @Test
    fun registrationFailIfWrongLoginValueAndPasswordEntered() {
        mockServer.dispatcher = NotFoundStatusDispatcher()
        onView(ViewMatchers.withId(R.id.emailText)).perform(
            ViewActions.typeText("u@mail.ru"),
            ViewActions.closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.passwordText)).perform(
            ViewActions.typeText("password"),
            ViewActions.closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.backButton)).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
        onView(ViewMatchers.withId(R.id.registerButton)).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }

    @Test
    fun registrationSuccessfulIfLoginValueAndPasswordCorrect() {
        mockServer.dispatcher = SuccessLoginOrRegisterDispatcher()
        onView(ViewMatchers.withId(R.id.emailText)).perform(
            ViewActions.typeText("${Date().time}@mail.ru"),
            ViewActions.closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.passwordText)).perform(
            ViewActions.typeText("password"),
            ViewActions.closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click())
        onView(ViewMatchers.withId(com.otus.homework.taskslist.R.id.tasksList)).check(
            ViewAssertions.matches(
                ViewMatchers.isEnabled()
            )
        )
    }

    @Test
    fun navigateToRegistrationViewIfRegisterButtonTap() {
        onView(ViewMatchers.withId(R.id.backButton)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.loginLayout)).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }
}