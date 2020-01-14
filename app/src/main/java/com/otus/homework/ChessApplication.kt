package com.otus.homework

import android.app.Application
import com.otus.homework.network.RetrofitBuilder
import com.otus.homework.network.interfaces.BackendApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ChessApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        bind<BackendApi>() with singleton { RetrofitBuilder() }
    }
}