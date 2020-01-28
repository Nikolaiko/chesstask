package com.example.core_api.network

import com.example.core_api.model.enums.ChessTaskDifficulty
import com.example.core_api.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import okhttp3.ResponseBody
import retrofit2.Response
import io.reactivex.Single

interface OnBoardingApi {
    fun login(newUserData:UserShortData): Single<Response<ResponseBody>>
    fun register(newUserData:UserShortData):Single<Response<UserShortData>>
    fun getRandomTask(difficulty: ChessTaskDifficulty):Single<Response<ChessTask>>
}