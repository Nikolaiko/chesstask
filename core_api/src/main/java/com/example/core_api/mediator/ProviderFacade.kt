package com.example.core_api.mediator

import com.example.core_api.utils.UserDataProvider

interface ProviderFacade : AppProvider, UserDataProvider, MediatorsProvider, NetworkProvider