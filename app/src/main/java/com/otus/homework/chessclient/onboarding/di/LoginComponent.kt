package com.otus.homework.chessclient.onboarding.di

import com.otus.homework.chessclient.core.di.CoreComponent
import com.otus.homework.chessclient.onboarding.views.LoginFragment
import dagger.Component

@LoginScope
@Component(
    modules = [LoginBinds::class],
    dependencies = [CoreComponent::class]
)
interface LoginComponent {
    fun inject(fragment:LoginFragment)
}
