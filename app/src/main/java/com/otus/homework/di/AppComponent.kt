package com.otus.homework.di

import android.app.Application
import android.content.Context
import com.example.core.app.ProvidersFacade
import com.otus.homework.ChessApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        MediatorsBindings::class
    ]
)
interface AppComponent : ProvidersFacade {

    companion object {
        private var appComponent: ProvidersFacade? = null

        fun create(application: Application): ProvidersFacade {
            return appComponent ?: DaggerAppComponent.builder()
                .application(application.applicationContext)
                .build().also {
                    appComponent = it
                }
        }
    }

    fun injects(app: ChessApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder
        fun build(): AppComponent
    }
}