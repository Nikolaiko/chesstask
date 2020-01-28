package com.otus.homework.onboarding.di

import com.example.core_api.mediator.ProviderFacade
import com.otus.homework.onboarding.views.LoginFragment
import dagger.Component

@LoginScope
@Component(
    modules = [LoginBinds::class],
    dependencies = [ProviderFacade::class]
)
interface LoginComponent {
    fun inject(fragment:LoginFragment)
}
