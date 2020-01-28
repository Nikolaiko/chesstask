package com.core_impl.utils

import android.content.SharedPreferences
import com.example.core_api.model.UserProfile
import com.example.core_api.utils.LoggedUserProvider
import javax.inject.Inject

class LoggedUserManager @Inject constructor(val sharedPreferences: SharedPreferences) : LoggedUserProvider {
    companion object {
        private const val USER_NAME_KEY:String = "logged_user_name"
        private const val USER_REFRESH_TOKEN_KEY:String = "logged_user_refresh_token"
        private const val USER_TOKEN_KEY:String = "logged_user_token"
        private const val DEFAULT_STRING_VALUE:String = ""
        private var loggedInUser:UserProfile? = null
    }

    override fun setLoggedUser(loggedUser:UserProfile) {
        loggedInUser = loggedUser
        val editor:SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_KEY, loggedUser.username)
        editor.putString(USER_TOKEN_KEY, loggedUser.token)
        editor.putString(USER_REFRESH_TOKEN_KEY, loggedUser.refreshToken)
        editor.apply()
    }

    override fun getLoggedUser(): UserProfile? {
        if (loggedInUser == null) {
            loggedInUser = tryToGetSavedUserProfile()
        }
        return loggedInUser
    }

    private fun tryToGetSavedUserProfile(): UserProfile? {
        var savedUser:UserProfile? = null
        val loggedUsername:String = sharedPreferences.getString(USER_NAME_KEY, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

        if (loggedUsername != DEFAULT_STRING_VALUE) {
            val token:String = sharedPreferences.getString(USER_TOKEN_KEY, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE
            val refreshToken:String = sharedPreferences.getString(USER_REFRESH_TOKEN_KEY, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE
            savedUser = UserProfile(loggedUsername, token, refreshToken)
        }
        return savedUser
    }
}