package com.otus.homework.network.interfaces

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserProfile
import com.otus.homework.model.user.UserShortData
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import io.reactivex.Single

interface UserApi {
    fun login(newUserData:UserShortData): Observable<UserProfile>
    fun register(newUserData:UserShortData): Observable<UserProfile>
    fun getRandomTask(difficulty:ChessTaskDifficulty): Observable<ChessTask>
}