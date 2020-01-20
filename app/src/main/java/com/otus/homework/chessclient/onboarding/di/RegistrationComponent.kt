package com.otus.homework.chessclient.onboarding.di

import com.otus.homework.chessclient.core.di.CoreComponent
import com.otus.homework.chessclient.onboarding.views.RegisterFragment
import dagger.Component

@RegisterScope
@Component(
    modules = [RegistrationBinds::class],
    dependencies = [CoreComponent::class]
)
interface RegistrationComponent {
    fun inject(fragment: RegisterFragment)
}