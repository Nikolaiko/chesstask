package com.otus.homework.network.implementations

import com.example.core.model.task.ChessTask
import com.otus.homework.network.BuildConfig
import com.otus.homework.network.interfaces.ChessTasksApi
import com.otus.homework.network.interfaces.ChessTasksService
import com.otus.homework.network.interfaces.RetrofitBuilder
import com.otus.homework.network.model.chesstasks.ChessTaskData
import com.otus.homework.network.model.chesstasks.ChessTaskShortData
import com.otus.homework.network.model.responses.Response
import io.reactivex.Observable
import javax.inject.Inject

class ChessTasksApiImpl @Inject constructor(builder: RetrofitBuilder) : ChessTasksApi {
    private val service = builder.buildChessTasksService()

    override fun getAllTasks(accessToken:String): Observable<Response<List<ChessTaskShortData>>> {
        println(BuildConfig.BASE_ADDRESS)
        return service.getTasksList(accessToken).map {
            if (it.isSuccessful) {
                Response<List<ChessTaskShortData>>(it.code(), it.message(), it.body())
            } else {
                throw Exception(it.message())
            }
        }.toObservable()
    }

    override fun getTaskById(accessToken: String, id: String): Observable<Response<ChessTaskData>> {
        return service.getTaskById(accessToken, id).map {
            if (it.isSuccessful) {
                Response<ChessTaskData>(it.code(), it.message(), it.body())
            } else {
                throw Exception(it.message())
            }
        }.toObservable()
    }
}