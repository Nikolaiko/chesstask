package com.otus.homework.app

import android.widget.Button
import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.*
import com.otus.homework.ChessApplication
import com.otus.homework.onboarding.OnBoardingActivity
import com.otus.homework.taskslist.TasksListActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24], application = ChessApplication::class)
class TasksListActivityTest {

    @Test
    fun `onBoarding activity should start after logout button tapped`() {
        val tasksListActivity = Robolectric.buildActivity(TasksListActivity::class.java)
            .create()
            .resume()
            .get()

        val logoutButton = tasksListActivity.findViewById<Button>(com.otus.homework.taskslist.R.id.logoutButton)
        assertNotNull(logoutButton)
        logoutButton.performClick()

        val intent = Shadows.shadowOf(tasksListActivity).peekNextStartedActivity()
        assertEquals(OnBoardingActivity::class.qualifiedName, intent.component?.className)
    }
}