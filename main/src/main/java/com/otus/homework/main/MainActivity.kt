package com.otus.homework.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core_api.mediator.AppWithFacade
import com.example.core_api.mediator.OnBoardingMediator
import com.example.core_api.utils.LoggedUserProvider
import com.otus.homework.main.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var loggedDataManager:LoggedUserProvider

    @Inject
    lateinit var onBoardingMediator:OnBoardingMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_module)

        MainComponent.init((application as AppWithFacade).getFacade()).injects(this)
        val loggedUser = loggedDataManager.getLoggedUser()

        if (loggedUser == null) {
            onBoardingMediator.createOnBoardingActivity(this)
            finish()
        }

        println("Logged user data :  ${loggedDataManager.getLoggedUser()} ")
    }
}