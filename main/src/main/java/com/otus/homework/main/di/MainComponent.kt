package com.otus.homework.main.di

import com.example.core.app.ProvidersFacade
import com.otus.homework.main.MainActivity
import com.otus.homework.storage.di.UserDataComponent
import dagger.Component

@Component(
    dependencies = [
        ProvidersFacade::class,
        UserDataComponent::class
    ]
)
interface MainComponent {

    companion object {
        fun init(appFacade: ProvidersFacade):MainComponent = DaggerMainComponent
            .builder()
            .providersFacade(appFacade)
            .userDataComponent(UserDataComponent.init(appFacade))
            .build()
    }

    fun injects(activity:MainActivity)
}