package com.otus.homework.main.di

import com.example.core_api.mediator.AppProvider
import com.example.core_api.mediator.ProviderFacade
import com.otus.homework.main.MainActivity
import dagger.Component

@Component(dependencies = [ProviderFacade::class])
interface MainComponent {

    companion object {
        fun init(providerFacade:ProviderFacade):MainComponent = DaggerMainComponent
            .builder()
            .providerFacade(providerFacade)
            .build()
    }

    fun injects(activity:MainActivity)
}