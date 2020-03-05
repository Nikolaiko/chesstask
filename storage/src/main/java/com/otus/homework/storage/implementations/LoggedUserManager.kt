package com.otus.homework.storage.implementations

import android.content.SharedPreferences
import com.example.core.model.user.UserProfile
import com.example.core.model.user.UserTokens
import com.otus.homework.storage.interfaces.LoggedUserProvider
import javax.inject.Inject

class LoggedUserManager @Inject constructor(private val sharedPreferences: SharedPreferences) :
    LoggedUserProvider {
    companion object {
        private const val USER_NAME_KEY: String = "logged_user_name"
        private const val USER_NAME_PASSWORD: String = "logged_user_password"
        private const val USER_NAME_ACCESS_TOKEN: String = "logged_user_access_token"
        private const val DEFAULT_STRING_VALUE: String = ""

        private var loggedInUser: UserProfile? = null
        private var loggedUserTokens: UserTokens? = null
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

    override fun setLoggedUserTokens(tokens: UserTokens) {
        loggedUserTokens = tokens
        val editor:SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_ACCESS_TOKEN, tokens.accessToken)
        editor.apply()
    }

    override fun getLoggedUserTokens(): UserTokens? {
        if (loggedUserTokens == null) {
            loggedUserTokens = tryGetSavedUserTokens()
        }
        return loggedUserTokens
    }

    override fun logout() {
        loggedInUser = null
        loggedUserTokens = null
        sharedPreferences.edit()
            .remove(USER_NAME_ACCESS_TOKEN)
            .apply()
    }

    private fun tryGetSavedUserTokens(): UserTokens? {
        var tokens: UserTokens? = null
        val userToken: String = sharedPreferences.getString(
            USER_NAME_ACCESS_TOKEN,
            DEFAULT_STRING_VALUE
        ) ?: DEFAULT_STRING_VALUE

        if (userToken != DEFAULT_STRING_VALUE) {
            tokens = UserTokens(userToken)
        }
        return tokens
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
            savedUser =
                UserProfile(loggedUsername, password)
        }
        return savedUser
    }
}