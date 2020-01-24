package com.otus.homework.main.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideSharedPreferances(preferances:SharedPreferences):SharedPreferences = preferances
}