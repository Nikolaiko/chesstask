package com.otus.homework.network.implementations

import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.interfaces.RetrofitBuilder
import com.otus.homework.network.model.responses.ChessTaskResponse
import com.otus.homework.network.model.responses.UserDataResponse
import io.reactivex.Observable
import javax.inject.Inject

class OnBoardingApiImpl @Inject constructor(builder: RetrofitBuilder) :
    OnBoardingApi {
    private val service = builder.buildOnBoardingService()

    override fun login(newUserData: UserData): Observable<UserDataResponse> {
        return service.loginUser(newUserData).map {
            if (it.isSuccessful) {
                UserDataResponse(it.code(), it.message(), it.body())
            } else {
                throw Exception(it.message())
            }
        }.toObservable()
    }
    override fun register(newUserData: UserData): Observable<UserDataResponse> {
        return service.registerUser(newUserData).map {
            if (it.isSuccessful) {
                UserDataResponse(it.code(), it.message(), it.body())
            } else {
                throw Exception(it.message())
            }
        }.toObservable()
    }
}