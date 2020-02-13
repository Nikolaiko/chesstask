package com.otus.homework.chesstask.di

import com.example.core.app.ProvidersFacade
import com.otus.homework.chesstask.views.ChessTaskFragment
import dagger.Component

@Component(
    modules = [ChessTaskBinds::class],
    dependencies = [ProvidersFacade::class]
)
interface ChessTaskComponent {
    companion object {
        fun init(facadeComponent:ProvidersFacade): ChessTaskComponent = DaggerChessTaskComponent
            .builder()
            .providersFacade(facadeComponent)
            .build()
    }

    fun inject(fragment: ChessTaskFragment)
}
