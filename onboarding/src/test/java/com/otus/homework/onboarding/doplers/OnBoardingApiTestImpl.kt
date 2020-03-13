package com.otus.homework.onboarding.doplers

import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.model.responses.UserDataResponse
import io.reactivex.Observable

class OnBoardingApiTestImpl : OnBoardingApi {
    override fun login(newUserData: UserData): Observable<UserDataResponse> {

    }

    override fun register(newUserData: UserData): Observable<UserDataResponse> {

    }
}