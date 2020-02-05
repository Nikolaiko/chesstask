package com.otus.homework.storage.di

import com.example.core.app.ProvidersFacade
import com.otus.homework.network.di.DaggerNetworkComponent
import com.otus.homework.network.di.NetworkComponent
import com.otus.homework.storage.implementations.ChessTasksRepository
import com.otus.homework.storage.implementations.UserDataRepository
import com.otus.homework.storage.interfaces.LoggedUserProvider
import dagger.Component

@Component(
    modules = [
        UserDataBindings::class
    ],
    dependencies = [
        NetworkComponent::class,
        ProvidersFacade::class
    ]
)
interface UserDataComponent {
    companion object {
        fun init(facadeApp: ProvidersFacade): UserDataComponent = DaggerUserDataComponent
            .builder()
            .providersFacade(facadeApp)
            .networkComponent(DaggerNetworkComponent.builder().build())
            .build()
    }

    fun provideLoggedUserManager(): LoggedUserProvider
    fun provideUserDataRepository(): UserDataRepository
    fun provideChessTasksRepository(): ChessTasksRepository
}