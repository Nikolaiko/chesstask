package com.otus.homework.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    private val gsonBuilder = GsonBuilder()
    private val retrofit = Retrofit.Builder()
        .baseUrl("'http://172.17.9.96:2020/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .build()


}