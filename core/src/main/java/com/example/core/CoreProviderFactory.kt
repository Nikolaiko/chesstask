package com.example.core

import com.core_impl.utils.DaggerUserDataComponent
import com.example.core_api.mediator.AppProvider
import com.example.core_api.mediator.NetworkProvider
import com.example.core_api.utils.UserDataProvider
import com.otus.homework.network.di.DaggerNetworkComponent
import com.otus.homework.network.di.NetworkComponent

object CoreProviderFactory {
    fun buildUserDataComponent(appProvider: AppProvider):UserDataProvider {
        return DaggerUserDataComponent.builder().appProvider(appProvider).build()
    }

    fun buildNetworkComponent():NetworkComponent {
        return DaggerNetworkComponent.builder().build()
    }
}