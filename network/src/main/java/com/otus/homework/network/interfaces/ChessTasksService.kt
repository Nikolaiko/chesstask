package com.otus.homework.network.interfaces

import com.example.core.model.task.ChessTask
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ChessTasksService {
    @GET("tasks/get")
    fun getRandomTask(
        @Header("Authorization") token:String,
        @Query("difficulty") difficulty:String
    ): Single<Response<ChessTask>>
}