package com.otus.homework.network.interfaces

import com.example.core.model.task.ChessTask
import com.otus.homework.network.model.chesstasks.ChessTaskData
import com.otus.homework.network.model.chesstasks.ChessTaskShortData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ChessTasksService {
    @GET("tasks")
    fun getTasksList(
        @Header("Authorization") token:String
    ): Single<Response<List<ChessTaskShortData>>>

    @GET("tasks/get")
    fun getRandomTask(
        @Header("Authorization") token:String,
        @Query("difficulty") difficulty:String
    ): Single<Response<ChessTaskData>>
}