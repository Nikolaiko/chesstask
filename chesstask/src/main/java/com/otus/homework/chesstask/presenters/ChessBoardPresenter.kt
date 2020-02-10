package com.otus.homework.chesstask.presenters

import com.example.core.model.task.ChessTask
import com.otus.homework.chesstask.reducers.BoardReducer
import com.otus.homework.chesstask.views.ChessTaskView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChessBoardPresenter @Inject constructor(
    private val reducer: BoardReducer
) : BoardPresenter {
    private var presenterView: ChessTaskView? = null
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun setBoardTask(task: ChessTask) {
        reducer.initChessTask(task)
    }

    override fun attachView(view: ChessTaskView) {
        presenterView = view
        bind()
    }

    override fun detachView() {
        presenterView = null
        disposeBag.clear()
    }

    private fun bind() {
        reducer.updateBoardPosition
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.updateChessBoardPosition(it)
            }.addTo(disposeBag)

        reducer.updateBoardCellSelection
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.updateChessBoardSelection(it)
            }.addTo(disposeBag)

        reducer.applyBoardAction
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.applyAction(it)
            }.addTo(disposeBag)

        presenterView?.selectedFigureId?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.selectFigureById(it)
        }?.addTo(disposeBag)

        presenterView?.selectedCell?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.selectCellAt(it)
        }?.addTo(disposeBag)
    }
}