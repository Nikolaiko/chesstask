package com.otus.homework.storage.implementations

import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.task.ChessTask
import com.example.core.model.user.UserTokens
import com.otus.homework.network.interfaces.ChessTasksApi
import com.example.core.model.task.ChessTaskShortInfo
import com.otus.homework.storage.getStartingColor
import com.otus.homework.storage.getStartingPositions
import com.otus.homework.storage.parsePgnString
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

    fun getTaskById(userTokens: UserTokens, id: String): Observable<ChessTask> {
        return api.getTaskById("Bearer ${userTokens.accessToken}", id).map {
            if (it.responseObject != null) {
                parsePgnString(it.responseObject!!.pgn)
                ChessTask(
                    it.responseObject!!.id,
                    getStartingPositions(it.responseObject!!.fen),
                    getStartingColor(it.responseObject!!.fen),
                    parsePgnString(it.responseObject!!.pgn)
                )
            } else {
                throw Exception(it.message)
            }
        }
    }
}