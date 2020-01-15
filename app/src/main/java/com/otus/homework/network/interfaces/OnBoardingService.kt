package com.otus.homework.network.interfaces

import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

interface OnBoardingService {
    @POST("user/signup")
    fun registerUser(@Body userData:UserShortData): Observable<Result<UserShortData>>

    @POST("user/signin")
    fun loginUser(@Body userData:UserShortData): Observable<Result<ResponseBody>>

    @GET("tasks/get")
    fun getRandomTask(@Query("difficulty") difficulty:String): Observable<Result<ChessTask>>
}