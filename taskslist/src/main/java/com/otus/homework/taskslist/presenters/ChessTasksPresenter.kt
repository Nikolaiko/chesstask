package com.otus.homework.taskslist.presenters

import com.otus.homework.taskslist.model.TasksListState
import com.otus.homework.taskslist.model.enums.TasksListScreens
import com.otus.homework.taskslist.reducer.TasksReducer
import com.otus.homework.taskslist.views.TasksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChessTasksPresenter @Inject constructor(private val reducer:TasksReducer) : TasksPresenter {
    private var presenterView:TasksView? = null
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: TasksView) {
        presenterView = view
        bind()
        reducer.refreshTasks()
    }

    override fun detachView() {
        presenterView = null
        disposeBag.clear()
        reducer.clearDisposables()
    }

    private fun bind() {
        presenterView?.selectedTask?.subscribe {
            reducer.getTaskById(it.id)
        }?.addTo(disposeBag)

        reducer.updateState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateState(it)
            }.addTo(disposeBag)
    }

    private fun updateState(newState:TasksListState) {
        presenterView?.setLoadingVisibility(newState.loadingActive)

        if (newState.loadedTasks != null) {
            presenterView?.updateTasksList(newState.loadedTasks)
        }
        
        if (newState.loadedTask != null) {
            presenterView?.navigateTo(TasksListScreens.SINGLE_CHESS_TASK, newState.loadedTask)
        }
    }
}
