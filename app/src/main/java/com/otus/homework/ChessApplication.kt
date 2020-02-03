package com.otus.homework

import android.app.Application
import com.otus.homework.di.AppComponent

class ChessApplication : Application() {
    companion object {
        private var appComponentObject: AppComponent? = null
    }

    fun getAppContextComponent(): AppComponent {
        return appComponentObject ?: D.also {
            appComponentObject = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        getAppContextComponent().injects(this)
    }
}