package com.otus.homework

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class ChessApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {

    }
}