package com.otus.homework.storage.di

import com.example.core_api.mediator.NetworkProvider
import com.otus.homework.network.di.NetworkComponent
import com.otus.homework.storage.UserDataRepository
import dagger.Component
import dagger.Provides

@Component(
   dependencies = [NetworkComponent::class]
)
interface UserDataComponent {
    fun provideUserDataRepository():UserDataRepository
}