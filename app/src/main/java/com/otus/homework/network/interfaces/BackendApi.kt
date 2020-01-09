package com.otus.homework.network.interfaces

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import com.otus.homework.utils.BasicCallback
import com.otus.homework.utils.ChessTaskCallback
import com.otus.homework.utils.RegisterUserCallback
import com.otus.homework.utils.RequestErrorCallback

interface BackendApi {
    fun login(newUserData:UserShortData, loginCallback:BasicCallback, errorCallback:RequestErrorCallback?)
    fun register(newUserData:UserShortData, registerCallback:RegisterUserCallback, errorCallback:RequestErrorCallback?)
    fun getRandomTask(difficulty: ChessTaskDifficulty, taskCallback:ChessTaskCallback, errorCallback:RequestErrorCallback?)
}