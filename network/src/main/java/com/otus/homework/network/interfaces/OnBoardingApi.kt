package com.otus.homework.network.interfaces

import com.otus.homework.model.user.UserData
import com.otus.homework.network.model.responses.ChessTaskResponse
import com.otus.homework.network.model.responses.UserDataResponse
import io.reactivex.Observable

interface OnBoardingApi {
    fun login(newUserData:UserData): Observable<UserDataResponse>
    fun register(newUserData:UserData):Observable<UserDataResponse>
    fun getRandomTask(difficulty: String):Observable<ChessTaskResponse>
}