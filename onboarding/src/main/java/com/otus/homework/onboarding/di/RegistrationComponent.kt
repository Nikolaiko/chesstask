package com.otus.homework.onboarding.di

import com.example.core.app.ProvidersFacade
import com.otus.homework.onboarding.views.RegisterFragment
import com.otus.homework.storage.di.UserDataComponent
import dagger.Component

@RegisterScope
@Component(
    modules = [RegistrationBinds::class],
    dependencies = [
        ProvidersFacade::class,
        UserDataComponent::class
    ]
)
interface RegistrationComponent {
    companion object {
        fun init(providersFacade: ProvidersFacade): RegistrationComponent = DaggerRegistrationComponent
            .builder()
            .providersFacade(providersFacade)
            .userDataComponent(UserDataComponent.init(providersFacade))
            .build()
    }
    fun inject(fragment: RegisterFragment)
}