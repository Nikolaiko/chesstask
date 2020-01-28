package com.otus.homework.network

import com.example.core_api.model.enums.ChessTaskDifficulty
import com.example.core_api.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import com.example.core_api.network.OnBoardingApi
import com.example.core_api.network.RetrofitBuilder
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class OnBoardingApiImpl @Inject constructor(private val builder: RetrofitBuilder) :
    OnBoardingApi {
    private val service = builder.buildOnBoardingService()

    override fun login(newUserData: UserShortData): Single<Response<ResponseBody>> = service.loginUser(newUserData)
    override fun register(newUserData: UserShortData): Single<Response<UserShortData>> = service.registerUser(newUserData)
    override fun getRandomTask(difficulty: ChessTaskDifficulty):Single<Response<ChessTask>> = service.getRandomTask(difficulty.name)
}