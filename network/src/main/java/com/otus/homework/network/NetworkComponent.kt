package com.otus.homework.network

import com.example.core_api.mediator.NetworkProvider
import dagger.Component

@Component(
    modules = [NetworkBinds::class]
)
interface NetworkComponent : NetworkProvider