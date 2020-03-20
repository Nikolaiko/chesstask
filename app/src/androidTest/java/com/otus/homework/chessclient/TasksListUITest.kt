package com.otus.homework.chessclient

import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.otus.homework.main.MainActivity
import com.otus.homework.taskslist.TasksListActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TasksListUITest {

    private val mockServer = MockWebServer()

    @get:Rule
    var activityRule: ActivityTestRule<TasksListActivity> = ActivityTestRule<TasksListActivity>(
        TasksListActivity::class.java, false, false
    )

    @Before
    fun setup() {
        mockServer.start(8080)
        activityRule.launchActivity(null)

        try {
            Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (exception: NoMatchingViewException) {
            Espresso.onView((ViewMatchers.withId(com.otus.homework.taskslist.R.id.logoutButton)))
                .perform(ViewActions.click())
        } finally {
            Espresso.onView((ViewMatchers.withId(com.otus.homework.onboarding.R.id.registerButton)))
                .perform(ViewActions.click())
        }
    }
}