package com.otus.homework.network.implementations

import com.otus.homework.model.user.UserData
import com.otus.homework.network.interfaces.OnBoardingApi
import com.otus.homework.network.interfaces.RetrofitBuilder
import com.otus.homework.network.model.responses.ChessTaskResponse
import com.otus.homework.network.model.responses.UserDataResponse
import io.reactivex.Observable
import javax.inject.Inject

class OnBoardingApiImpl @Inject constructor(private val builder: RetrofitBuilder) :
    OnBoardingApi {
    private val service = builder.buildOnBoardingService()

    override fun login(newUserData: UserData): Observable<UserDataResponse> {
        return service.loginUser(newUserData).map {
            UserDataResponse(it.code(), it.message(), it.body())
        }.toObservable()
    }
    override fun register(newUserData: UserData): Observable<UserDataResponse> {
        return service.registerUser(newUserData).map {
            UserDataResponse(it.code(), it.message(), it.body())
        }.toObservable()
    }
    override fun getRandomTask(difficulty: String):Observable<ChessTaskResponse>  {
        return service.getRandomTask(difficulty).map {
            ChessTaskResponse(it.code(), it.message(), it.body())
        }.toObservable()
    }
}