package com.otus.homework.network.interfaces

import com.example.core.model.task.ChessTask
import com.otus.homework.model.user.UserData
import com.otus.homework.network.model.chesstasks.ChessTaskData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OnBoardingService {
    @POST("user/signup")
    fun registerUser(@Body userData:UserData): Single<Response<UserData>>

    @POST("user/signin")
    fun loginUser(@Body userData:UserData): Single<Response<UserData>>

    @GET("tasks/get")
    fun getRandomTask(@Query("difficulty") difficulty:String): Single<Response<ChessTaskData>>
}