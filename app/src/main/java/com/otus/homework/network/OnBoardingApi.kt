package com.otus.homework.network

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.IOnBoardingApi
import com.otus.homework.network.interfaces.IRetrofitBuilder
import okhttp3.ResponseBody
import rx.Observable

class OnBoardingApi(private val builder:IRetrofitBuilder) : IOnBoardingApi {
    private val service = builder.buildOnBoardingService()

    override fun login(newUserData: UserShortData): Observable<Result<ResponseBody>> = service.loginUser(newUserData)
    override fun register(newUserData: UserShortData): Observable<Result<UserShortData>> = service.registerUser(newUserData)
    override fun getRandomTask(difficulty: ChessTaskDifficulty) = service.getRandomTask(difficulty.name)
}