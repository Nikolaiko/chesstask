package com.otus.homework.network

import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserProfile
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.UserApi
import com.otus.homework.network.interfaces.NetworkEngineBuilder
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class OnBoardingApi(builder:NetworkEngineBuilder) : UserApi {
    private val service = builder.buildOnBoardingService()

    override fun login(newUserData: UserShortData): Observable<UserProfile> {
        return service.loginUser(newUserData).map {
            if (it.isSuccessful && it.body() != null) {
                UserProfile(it.body()!!.email, it.body()!!.password)
            } else {
                throw Exception(it.message())
            }
        }.toObservable()
    }
    override fun register(newUserData: UserShortData): Observable<UserProfile> {
        return service.registerUser(newUserData).map {
            if (it.isSuccessful && it.body() != null) {
                UserProfile(it.body()!!.email, it.body()!!.password)
            } else {
                throw Exception(it.message())
            }
        }.toObservable()
    }
    override fun getRandomTask(difficulty: ChessTaskDifficulty): Observable<ChessTask> {
        return service.getRandomTask(difficulty.name).map {
            if (it.isSuccessful && it.body() != null) {
                it.body()!!
            } else {
                throw Exception(it.message())
            }
        }    .toObservable()
    }
}