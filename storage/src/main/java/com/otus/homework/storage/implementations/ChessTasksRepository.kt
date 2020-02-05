package com.otus.homework.storage.implementations

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.task.ChessTask
import com.example.core.model.user.UserTokens
import com.otus.homework.network.interfaces.ChessTasksApi
import com.example.core.model.task.ChessTaskShortInfo
import io.reactivex.Observable
import javax.inject.Inject

class ChessTasksRepository @Inject constructor(private val api:ChessTasksApi) {
    fun getAllTasks(userTokens: UserTokens): Observable<List<ChessTaskShortInfo>> {
        return api.getAllTasks("Bearer ${userTokens.accessToken}").map {
            if (it.responseObject != null) {
               it.responseObject!!.map { currentTask ->
                   ChessTaskShortInfo(currentTask.id, currentTask.name)
               }
            } else {
                throw Exception(it.message)
            }
        }
    }
}