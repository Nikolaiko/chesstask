package com.core_impl.utils

import com.example.core_api.mediator.AppProvider
import com.example.core_api.utils.UserDataProvider
import dagger.Component

@Component(
    dependencies = [AppProvider::class],
    modules = [UserDataModule::class]
)
interface UserDataComponent : UserDataProvider