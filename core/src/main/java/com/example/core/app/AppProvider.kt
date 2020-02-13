package com.example.core.app

import android.content.Context
import android.content.SharedPreferences

interface AppProvider {
    fun provideContext(): Context
    fun provideSharedPreferences(): SharedPreferences
}
