package com.core_impl.utils

import android.content.SharedPreferences
import com.example.core_api.utils.LoggedUserProvider
import dagger.Module
import dagger.Provides

@Module
class UserDataModule {
    @Provides
    fun providesLoggedUserManager(sharedPreferences: SharedPreferences):LoggedUserProvider {
        return LoggedUserManager(sharedPreferences)
    }
}