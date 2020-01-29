package com.otus.homework.di

import android.app.Application
import com.example.core.CoreProviderFactory
import com.example.core_api.mediator.AppProvider
import com.example.core_api.mediator.NetworkProvider
import com.example.core_api.mediator.ProviderFacade
import com.example.core_api.utils.UserDataProvider
import com.otus.homework.ChessApplication
import dagger.Component

@Component(
    dependencies = [
        AppProvider::class,
        UserDataProvider::class,
        NetworkProvider::class
    ],
    modules = [MediatorsBindings::class]
)
interface FacadeComponent : ProviderFacade {
    companion object {
        fun init(application: Application) : FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .userDataProvider(CoreProviderFactory.buildUserDataComponent(
                    AppComponent.create(
                        application
                    )
                ))
                .networkProvider(CoreProviderFactory.buildNetworkComponent())
                .build()
    }

    fun injects(app: ChessApplication)
}