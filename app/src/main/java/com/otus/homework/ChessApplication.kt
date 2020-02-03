package com.otus.homework

import android.app.Application
import com.example.core.app.AppWithFacade
import com.example.core.app.ProvidersFacade
import com.otus.homework.di.AppComponent

class ChessApplication : Application(),
    AppWithFacade {
    companion object {
        private var appComponentObject: ProvidersFacade? = null
    }

    override fun getFacade(): ProvidersFacade {
        return appComponentObject ?: AppComponent.create(this).also {
            appComponentObject = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        (getFacade() as AppComponent).injects(this)
    }
}