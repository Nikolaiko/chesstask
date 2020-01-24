package com.otus.homework.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core_api.mediator.AppWithFacade
import com.otus.homework.main.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    //@Inject
    //lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_module)

        MainComponent.init((application as AppWithFacade).getFacade()).injects(this)

        //println(sharedPreferences)
    }
}