package com.otus.homework

import android.app.Application
import android.content.SharedPreferences
import com.example.core_api.mediator.AppWithFacade
import com.example.core_api.mediator.ProviderFacade
import com.otus.homework.di.FacadeComponent
import javax.inject.Inject

class ChessApplication : Application(), AppWithFacade {

    @Inject
    lateinit var shared: SharedPreferences

    companion object {
        private var facadeObject: FacadeComponent? = null
    }

    override fun getFacade(): ProviderFacade {
        return facadeObject ?: FacadeComponent.init(this).also {
            facadeObject = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        (getFacade() as FacadeComponent).injects(this)
    }
}