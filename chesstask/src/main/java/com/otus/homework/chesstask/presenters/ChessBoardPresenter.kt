package com.otus.homework.chesstask.presenters

import com.example.core.model.task.ChessTask
import com.otus.homework.chesstask.model.ChessTaskMessageId
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

    override fun openSolution() {
        reducer.openSolution()
    }

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

        reducer.updateNews
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when(it.newsId) {
                    ChessTaskMessageId.WRONG_MOVE -> presenterView?.showWrongMoveDialog()
                    ChessTaskMessageId.GAME_FINISHED -> presenterView?.closeView()
                    ChessTaskMessageId.GAME_WON -> presenterView?.showWinDialog()
                    ChessTaskMessageId.OPEN_SOLUTION -> {
                        presenterView?.showSolutionText()
                        presenterView?.hideOpenSolutionButton()
                    }
                    ChessTaskMessageId.CANT_FIND_FIGURE_BY_ID -> presenterView?.showWrongFigureMessage()
                }
            }.addTo(disposeBag)

        presenterView?.selectedFigureId?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.selectFigureById(it)
        }?.addTo(disposeBag)

        presenterView?.selectedCell?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.selectCellAt(it)
        }?.addTo(disposeBag)

        presenterView?.undoButton?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.undoLastMove()
        }?.addTo(disposeBag)

        presenterView?.restartButton?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.restartTask()
        }?.addTo(disposeBag)

        presenterView?.exitButton?.subscribeOn(Schedulers.io())?.subscribe {
            reducer.exitTask()
        }?.addTo(disposeBag)
    }
}
