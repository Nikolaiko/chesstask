package com.otus.homework.onboarding

import android.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class LoginUITest {

    @get:Rule
    var activityRule: ActivityTestRule<OnBoardingActivity> = ActivityTestRule<OnBoardingActivity>(
        OnBoardingActivity::class.java, false, true
    )

    @Test
    fun appFromStart_EnterLoginCredentianals_ViewWithTasksList() {
        onView(withId(com.otus.homework.onboarding.R.id.loginButton)).check(matches(isEnabled()))
    }
}