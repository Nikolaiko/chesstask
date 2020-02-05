package com.otus.homework.taskslist.reducer

import com.otus.homework.storage.implementations.ChessTasksRepository
import com.otus.homework.storage.interfaces.LoggedUserProvider
import com.otus.homework.taskslist.model.TasksListNews
import com.otus.homework.taskslist.model.TasksListState
import com.otus.homework.taskslist.model.enums.NewsMessageId
import com.otus.homework.taskslist.model.enums.TasksListScreens
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TasksListReducer @Inject constructor(
    private val tasksRepository: ChessTasksRepository,
    private val loggedUserProvider: LoggedUserProvider
): TasksReducer {
    private val _updateState: PublishSubject<TasksListState> = PublishSubject.create()
    override val updateState: Observable<TasksListState>
        get() = _updateState

    private val _updateNews: PublishSubject<TasksListNews> = PublishSubject.create()
    override val updateNews: Observable<TasksListNews>
        get() = _updateNews

    private val _updateDestination: PublishSubject<TasksListScreens> = PublishSubject.create()
    override val updateDestination: Observable<TasksListScreens>
        get() = _updateDestination

    private var state:TasksListState = TasksListState()
    private val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun refreshTasks() {
        state = TasksListState(loadingActive = true)
        _updateState.onNext(state)

        val userTokens = loggedUserProvider.getLoggedUserTokens()
        if (userTokens != null) {
            tasksRepository.getAllTasks(userTokens)
                .subscribeOn(Schedulers.io())
                .subscribe( {
                state = TasksListState(loadedTasks = it)
                _updateState.onNext(state)
            }, {
                println("Error message : ${it.message}")
                state = TasksListState()
                _updateState.onNext(state)
                _updateNews.onNext(TasksListNews(NewsMessageId.EXCEPTION_TASKS_LIST_REQUEST, it.message ?: ""))
            }).addTo(disposeBag)
        } else {
            state = TasksListState()
            _updateState.onNext(state)
            //news about error    not logged in
        }
    }

    override fun clearDisposables() {
        disposeBag.clear()
    }
}