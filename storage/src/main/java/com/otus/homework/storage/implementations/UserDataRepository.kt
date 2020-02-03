package com.otus.homework.storage.implementations

import com.example.core.model.UserProfile
import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import io.reactivex.Observable
import javax.inject.Inject

class UserDataRepository @Inject constructor(private val onBoardingApi:OnBoardingApi) {
    fun login(userProfile: UserProfile): Observable<UserProfile> {
        return onBoardingApi.login(UserData(userProfile.username, userProfile.password)).map {
            if (it.userData != null) {
                UserProfile(it.userData!!.email, it.userData!!.password)
            } else {
                throw Exception(it.message)
            }
        }
    }

    fun registerNewUser(userProfile: UserProfile): Observable<UserProfile> {
        return onBoardingApi.register(UserData(userProfile.username, userProfile.password)).map {
            if (it.userData != null) {
                UserProfile(it.userData!!.email, it.userData!!.password)
            } else {
                throw Exception(it.message)
            }
        }
    }
}