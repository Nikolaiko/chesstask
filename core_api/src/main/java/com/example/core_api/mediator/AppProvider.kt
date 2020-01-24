package com.example.core_api.mediator

import android.content.Context
import android.content.SharedPreferences

interface AppProvider {
    fun provideContext():Context
    fun provideSharedPreferences(context: Context): SharedPreferences
}