package com.otus.homework.storage.implementations

import android.content.SharedPreferences
import com.example.core.model.UserProfile
import com.otus.homework.storage.interfaces.LoggedUserProvider
import javax.inject.Inject

class LoggedUserManager @Inject constructor(val sharedPreferences: SharedPreferences) :
    LoggedUserProvider {
    companion object {
        private const val USER_NAME_KEY: String = "logged_user_name"
        private const val USER_NAME_PASSWORD: String = "logged_user_password"
        private const val DEFAULT_STRING_VALUE: String = ""
        private var loggedInUser: UserProfile? = null
    }

    override fun setLoggedUser(loggedUser: UserProfile) {
        loggedInUser = loggedUser
        val editor:SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_KEY, loggedUser.username)
        editor.putString(USER_NAME_PASSWORD, loggedUser.password)
        editor.apply()
    }

    override fun getLoggedUser(): UserProfile? {
        if (loggedInUser == null) {
            loggedInUser = tryToGetSavedUserProfile()
        }
        return loggedInUser
    }

    private fun tryToGetSavedUserProfile(): UserProfile? {
        var savedUser: UserProfile? = null
        val loggedUsername: String = sharedPreferences.getString(
            USER_NAME_KEY,
            DEFAULT_STRING_VALUE
        ) ?: DEFAULT_STRING_VALUE

        if (loggedUsername != DEFAULT_STRING_VALUE) {
            val password: String = sharedPreferences.getString(
                USER_NAME_PASSWORD,
                DEFAULT_STRING_VALUE
            ) ?: DEFAULT_STRING_VALUE
            savedUser = UserProfile(loggedUsername, password)
        }
        return savedUser
    }
}