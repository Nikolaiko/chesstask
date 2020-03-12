package com.otus.homework.chessclient.matchers

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description?) {
        description?.appendText("It's a Toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        val rootValue = item ?: return false
        val type = rootValue.windowLayoutParams.get().type
        if (type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY) {
            val windowToken = rootValue.decorView.windowToken
            val appToken = rootValue.decorView.applicationWindowToken
            if (windowToken == appToken) {
                return true
            }
        }
        return false
    }
}