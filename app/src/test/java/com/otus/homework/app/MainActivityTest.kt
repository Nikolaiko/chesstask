package com.otus.homework.app

import com.otus.homework.ChessApplication
import com.otus.homework.main.MainActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24], application = ChessApplication::class)
class MainActivityTest {
    @Test
    fun `application name in resources should match expected app name`() {
        val expectedAppName = "Chess Application"
        val mainActivity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

        val actualAppName = mainActivity.resources.getString(com.otus.homework.main.R.string.app_name)
        Assert.assertEquals(expectedAppName, actualAppName)
    }
}