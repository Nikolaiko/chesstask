package com.otus.homework.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core_api.mediator.AppWithContext
import com.example.core_api.mediator.OnBoardingMediator
import com.example.core_api.mediator.TasksListMediator
import com.example.core_api.model.UserProfile
import com.otus.homework.main.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var onBoardingMediator:OnBoardingMediator

    @Inject
    lateinit var tasksListMediator: TasksListMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_module)

        MainComponent.init((application as AppWithContext).getFacade()).injects(this)
        val loggedUser:UserProfile? = null // = loggedDataManager.getLoggedUser()

        if (loggedUser == null) {
            onBoardingMediator.createOnBoardingActivity(this)
            finish()
        } else {
            tasksListMediator.createTasksListActivity(this)
            finish()
        }

        //println("Logged user data :  ${loggedDataManager.getLoggedUser()} ")
    }
}