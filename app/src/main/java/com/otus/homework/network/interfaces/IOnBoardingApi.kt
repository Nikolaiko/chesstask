package com.otus.homework.network.interfaces

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import okhttp3.ResponseBody
import retrofit2.Response
import io.reactivex.Single

interface IOnBoardingApi {
    fun login(newUserData:UserShortData): Single<Response<ResponseBody>>
    fun register(newUserData:UserShortData):Single<Response<UserShortData>>
    fun getRandomTask(difficulty:ChessTaskDifficulty):Single<Response<ChessTask>>
}