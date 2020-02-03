package com.otus.homework.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.app.AppWithFacade
import com.example.core.mediator.OnBoardingMediator
import com.example.core.mediator.TasksListMediator
import com.example.core.model.UserProfile
import com.otus.homework.main.di.MainComponent
import com.otus.homework.storage.interfaces.LoggedUserProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var loggedDataManager: LoggedUserProvider

    @Inject
    lateinit var onBoardingMediator:OnBoardingMediator

    @Inject
    lateinit var tasksListMediator: TasksListMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_module)

        MainComponent.init((application as AppWithFacade).getFacade()).injects(this)
        val loggedUser:UserProfile? = loggedDataManager.getLoggedUser()

        if (loggedUser == null) {
            onBoardingMediator.createOnBoardingActivity(this)
            finish()
        } else {
            tasksListMediator.createTasksListActivity(this)
            finish()
        }
    }
}