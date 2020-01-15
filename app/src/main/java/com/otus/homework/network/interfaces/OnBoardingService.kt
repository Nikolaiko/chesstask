package com.otus.homework.network.interfaces

import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OnBoardingService {
    @POST("user/signup")
    fun registerUser(@Body userData:UserShortData): Single<Response<UserShortData>>

    @POST("user/signin")
    fun loginUser(@Body userData:UserShortData): Single<Response<ResponseBody>>

    @GET("tasks/get")
    fun getRandomTask(@Query("difficulty") difficulty:String): Single<Response<ChessTask>>
}