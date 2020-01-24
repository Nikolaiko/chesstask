package com.otus.homework.chessclient.core

import android.app.Application
import com.example.core_api.mediator.AppWithFacade
import com.example.core_api.mediator.ProviderFacade
import com.otus.homework.FacadeComponent

class ChessApplication : Application(), AppWithFacade {
    companion object {
        private var facadeObject:FacadeComponent? = null
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