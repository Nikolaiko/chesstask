package com.otus.homework.chesstask.di

import com.otus.homework.chesstask.presenters.BoardPresenter
import com.otus.homework.chesstask.presenters.ChessBoardPresenter
import com.otus.homework.chesstask.reducers.BoardReducer
import com.otus.homework.chesstask.reducers.ChessBoardReducer
import dagger.Binds
import dagger.Module

@Module
interface ChessTaskBinds {
    @Binds
    fun bindBoardReducer(reducer: ChessBoardReducer): BoardReducer

    @Binds
    fun bindBoardPresenter(presenter: ChessBoardPresenter): BoardPresenter
}