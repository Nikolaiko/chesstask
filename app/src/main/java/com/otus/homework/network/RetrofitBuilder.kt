package com.otus.homework.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.deserializers.ChessTaskDeserializer
import com.otus.homework.network.interfaces.BackendApi
import com.otus.homework.network.interfaces.RetrofitService
import com.otus.homework.utils.BasicCallback
import com.otus.homework.utils.ChessTaskCallback
import com.otus.homework.utils.RegisterUserCallback
import com.otus.homework.utils.RequestErrorCallback
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder : BackendApi {
    companion object {
        private const val REQUEST_FAILURE_MESSAGE:String = "Request failure : "
        private const val REQUEST_ERROR_MESSAGE:String = "Request error : "
        private const val REQUEST_SUCCESS_CODE:Int = 200
    }

    private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder().addInterceptor(logger).build()

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(ChessTask::class.java, ChessTaskDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://92.242.40.194:80")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val service  = retrofit.create(RetrofitService::class.java)

    override fun login(
        newUserData: UserShortData,
        loginCallback: BasicCallback,
        errorCallback: RequestErrorCallback?
    ) {
        service.loginUser(newUserData).enqueue( object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                errorCallback?.invoke("$REQUEST_FAILURE_MESSAGE ${t?.localizedMessage}")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                when(response.code()) {
                    REQUEST_SUCCESS_CODE -> loginCallback()
                    else -> errorCallback?.invoke("$REQUEST_ERROR_MESSAGE ${response.message()} with status : ${response.code()}")
                }
            }
        })
    }

    override fun register(
        newUserData: UserShortData,
        registerCallback: RegisterUserCallback,
        errorCallback: RequestErrorCallback?
    ) {
        service.registerUser(newUserData).enqueue( object : Callback<UserShortData> {
            override fun onFailure(call: Call<UserShortData>?, t: Throwable?) {
                errorCallback?.invoke("$REQUEST_FAILURE_MESSAGE ${t?.localizedMessage}")
            }

            override fun onResponse(call: Call<UserShortData>?, response: Response<UserShortData>) {
                when(response.code()) {
                    REQUEST_SUCCESS_CODE -> registerCallback(response.body())
                    else -> errorCallback?.invoke("$REQUEST_ERROR_MESSAGE ${response.message()} with status : ${response.code()}")
                }
            }
        })
    }

    override fun getRandomTask(
        difficulty: ChessTaskDifficulty,
        taskCallback: ChessTaskCallback,
        errorCallback: RequestErrorCallback?
    ) {
        service.getRandomTask(difficulty.name).enqueue( object : Callback<ChessTask> {
            override fun onFailure(call: Call<ChessTask>?, t: Throwable?) {
                errorCallback?.invoke("$REQUEST_FAILURE_MESSAGE ${t?.localizedMessage}")
            }

            override fun onResponse(call: Call<ChessTask>?, response: Response<ChessTask>) {
                when(response.code()) {
                    REQUEST_SUCCESS_CODE -> taskCallback(response.body())
                    else -> errorCallback?.invoke("$REQUEST_ERROR_MESSAGE ${response.message()} with status : ${response.code()}")
                }
            }
        })
    }
}