package com.otus.homework.network.interfaces

import com.otus.homework.network.model.chesstasks.ChessTaskData
import com.otus.homework.network.model.chesstasks.ChessTaskShortData
import com.otus.homework.network.model.responses.Response
import io.reactivex.Observable

interface ChessTasksApi {
    fun getAllTasks(accessToken:String): Observable<Response<List<ChessTaskShortData>>>
    fun getTaskById(accessToken:String, id:String): Observable<Response<ChessTaskData>>
}