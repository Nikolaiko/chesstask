package com.otus.homework.taskslist.reducer

import com.otus.homework.storage.implementations.ChessTasksRepository
import com.otus.homework.storage.interfaces.LoggedUserProvider
import com.otus.homework.taskslist.RxJavaRule
import com.otus.homework.taskslist.model.TasksListState
import org.junit.Rule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class TasksListReducerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var chessTaskRepository: ChessTasksRepository

    @Mock
    lateinit var loggedUserProvider: LoggedUserProvider

    val loadingStateScreen = TasksListState(loadingActive = true, loadedTasks = null)
    val usualStateScreen = TasksListState(loadedTasks = null)


}