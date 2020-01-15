package com.otus.homework.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.otus.homework.model.task.ChessTask
import com.otus.homework.network.deserializers.ChessTaskDeserializer
import com.otus.homework.network.interfaces.IRetrofitBuilder
import com.otus.homework.network.interfaces.OnBoardingService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder : IRetrofitBuilder {
    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(ChessTask::class.java, ChessTaskDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://92.242.40.194:80")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    override fun buildOnBoardingService(): OnBoardingService = retrofit.create(OnBoardingService::class.java)
}