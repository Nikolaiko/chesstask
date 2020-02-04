package com.otus.homework.storage.implementations

import com.example.core.model.user.UserProfile
import com.example.core.model.user.UserTokens
import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import io.reactivex.Observable
import javax.inject.Inject

class UserDataRepository @Inject constructor(private val onBoardingApi:OnBoardingApi) {
    fun login(userProfile: UserProfile): Observable<UserTokens> {
        return onBoardingApi.login(UserData(userProfile.username, userProfile.password)).map {
            if (it.tokens != null) {
                UserTokens(
                    it.tokens!!.accessToken
                )
            } else {
                throw Exception(it.message)
            }
        }
    }

    fun registerNewUser(userProfile: UserProfile): Observable<UserTokens> {
        return onBoardingApi.register(UserData(userProfile.username, userProfile.password)).map {
            if (it.tokens != null) {
                UserTokens(
                    it.tokens!!.accessToken
                )
            } else {
                throw Exception(it.message)
            }
        }
    }
}