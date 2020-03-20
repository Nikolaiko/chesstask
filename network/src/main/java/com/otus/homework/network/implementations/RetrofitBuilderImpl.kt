package com.otus.homework.network.implementations

import com.example.core.model.task.ChessTask
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.otus.homework.network.interfaces.ChessTasksService
import com.otus.homework.network.interfaces.OnBoardingService
import com.otus.homework.network.interfaces.RetrofitBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.otus.homework.network.BuildConfig
import javax.inject.Inject

class RetrofitBuilderImpl @Inject constructor() :
    RetrofitBuilder {

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_ADDRESS)
        //.client(client)
        .client(OkHttpClient.Builder().build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    override fun buildOnBoardingService(): OnBoardingService = retrofit.create(
        OnBoardingService::class.java)

    override fun buildChessTasksService(): ChessTasksService = retrofit.create(
        ChessTasksService::class.java
    )
}