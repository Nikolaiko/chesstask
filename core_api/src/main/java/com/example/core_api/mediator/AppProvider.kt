package com.example.core_api.mediator

import android.content.Context
import android.content.SharedPreferences
import com.example.core_api.utils.ILoggedUserData

interface AppProvider {
    fun provideContext():Context
    fun provideSharedPreferences():SharedPreferences
}