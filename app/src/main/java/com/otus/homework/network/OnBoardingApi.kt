package com.otus.homework.network

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.IOnBoardingApi
import com.otus.homework.network.interfaces.IRetrofitBuilder
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

class OnBoardingApi(private val builder:IRetrofitBuilder) : IOnBoardingApi {
    private val service = builder.buildOnBoardingService()

    override fun login(newUserData: UserShortData): Single<Response<ResponseBody>> = service.loginUser(newUserData)
    override fun register(newUserData: UserShortData): Single<Response<UserShortData>> = service.registerUser(newUserData)
    override fun getRandomTask(difficulty: ChessTaskDifficulty):Single<Response<ChessTask>> = service.getRandomTask(difficulty.name)
}