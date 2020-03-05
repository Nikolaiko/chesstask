package com.otus.homework.chessclient

import android.app.Activity
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.otus.homework.di.AppModule

class FreshLoginTestRule<T : Activity>(activityClass:Class<T>, mode:Boolean, start:Boolean) : ActivityTestRule<T>(activityClass, mode, start), Parcelable {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences(AppModule.PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove("logged_user_name")
            .apply()
        println("Before Called")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {}
    override fun describeContents(): Int = 0
}