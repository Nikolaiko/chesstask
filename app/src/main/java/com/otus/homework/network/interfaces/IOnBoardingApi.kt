package com.otus.homework.network.interfaces

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import okhttp3.ResponseBody
import rx.Observable

interface IOnBoardingApi {
    fun login(newUserData:UserShortData):Observable<Result<ResponseBody>>
    fun register(newUserData:UserShortData):Observable<Result<UserShortData>>
    fun getRandomTask(difficulty:ChessTaskDifficulty):Observable<Result<ChessTask>>
}