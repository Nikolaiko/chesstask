package com.otus.homework

import android.app.Application
import com.example.core_api.mediator.AppProvider
import com.example.core_api.mediator.ProviderFacade
import com.otus.homework.chessclient.core.ChessApplication
import dagger.Component

@Component(dependencies = [AppProvider::class])
interface FacadeComponent : ProviderFacade {
    companion object {
        fun init(application: Application) : FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .build()
    }

    fun injects(app: ChessApplication)
}