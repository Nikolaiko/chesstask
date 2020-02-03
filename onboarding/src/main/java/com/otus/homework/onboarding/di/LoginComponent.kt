package com.otus.homework.onboarding.di

import com.example.core.app.AppProvider
import com.example.core.app.ProvidersFacade
import com.otus.homework.onboarding.views.LoginFragment
import com.otus.homework.storage.di.UserDataComponent
import dagger.Component

@LoginScope
@Component(
    modules = [LoginBinds::class],
    dependencies = [
        ProvidersFacade::class,
        UserDataComponent::class
    ]
)
interface LoginComponent {
    companion object {
        fun init(providersFacade: ProvidersFacade): LoginComponent = DaggerLoginComponent
            .builder()
            .providersFacade(providersFacade)
            .userDataComponent(UserDataComponent.init(providersFacade))
            .build()
    }

    fun inject(fragment:LoginFragment)
}
