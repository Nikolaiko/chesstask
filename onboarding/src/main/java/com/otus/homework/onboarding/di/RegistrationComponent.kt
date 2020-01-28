package com.otus.homework.onboarding.di

import com.example.core_api.mediator.ProviderFacade
import com.otus.homework.onboarding.views.RegisterFragment
import dagger.Component

@RegisterScope
@Component(
    modules = [RegistrationBinds::class],
    dependencies = [ProviderFacade::class]
)
interface RegistrationComponent {
    fun inject(fragment: RegisterFragment)
}