package com.otus.homework.taskslist.reducer

import com.example.core.model.enums.ChessTaskDifficulty
import com.example.core.model.task.ChessTaskShortInfo
import com.example.core.model.user.UserTokens
import com.otus.homework.storage.implementations.ChessTasksRepository
import com.otus.homework.storage.interfaces.LoggedUserProvider
import com.otus.homework.RxJavaRule
import com.otus.homework.network.interfaces.ChessTasksApi
import com.otus.homework.network.model.chesstasks.ChessTaskData
import com.otus.homework.network.model.chesstasks.ChessTaskShortData
import com.otus.homework.network.model.responses.Response
import com.otus.homework.taskslist.model.TasksListState
import com.otus.homework.taskslist.model.enums.NewsMessageId
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class TasksListReducerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var chessTaskApi: ChessTasksApi

    @Mock
    lateinit var loggedUserProvider: LoggedUserProvider

    private val dummyTaskFromServer = ChessTaskData(
        "1",
        "5r1k/p2n1p1p/5P1N/1p1p4/2pP3P/8/PP4RK/8 w - - 1 0",
        "1. Rg8+ Rxg8 2. Nxf7# ")

    private val dummyListFromServer = listOf(
        ChessTaskShortData("1", "testName"),
        ChessTaskShortData("2", "testName")
    )
    private val dummyParsedServerResponseList = listOf(
        ChessTaskShortInfo("1", "testName"),
        ChessTaskShortInfo("2", "testName")
    )

    private val loadingStateScreen = TasksListState(loadingActive = true, loadedTasks = null)
    private val loadedStateScreen = TasksListState(loadedTasks = dummyParsedServerResponseList)


    @Test
    fun `refresh task trigger loading`() {
        val dummySuccessResponse = Response(200, "Success", dummyListFromServer)

        Mockito.`when`(loggedUserProvider.getLoggedUserTokens())
            .thenReturn(UserTokens("123"))

        Mockito.`when`(chessTaskApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.just(dummySuccessResponse).toObservable())

        val chessTaskRepository = ChessTasksRepository(chessTaskApi)
        val reducer = TasksListReducer(chessTaskRepository, loggedUserProvider)

        val tasksListStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.refreshTasks()

        tasksListStateObserver.assertValueCount(2)
        assertEquals(loadedStateScreen, tasksListStateObserver.values()[1])
        assertEquals(loadingStateScreen, tasksListStateObserver.values()[0])
    }

    @Test
    fun `refresh task return tasks list`() {
        val dummySuccessResponse = Response(200, "Success", dummyListFromServer)

        Mockito.`when`(chessTaskApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.just(dummySuccessResponse).toObservable())

        Mockito.`when`(loggedUserProvider.getLoggedUserTokens())
            .thenReturn(UserTokens("123"))

        val chessTaskRepository = ChessTasksRepository(chessTaskApi)
        val reducer = TasksListReducer(chessTaskRepository, loggedUserProvider)

        val tasksListStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.refreshTasks()

        tasksListStateObserver.assertValueCount(2)
        assertEquals(dummyParsedServerResponseList, tasksListStateObserver.values()[1].loadedTasks)
    }

    @Test
    fun `get task by id return task`() {
        val dummySuccessResponse = Response(200, "Success", dummyTaskFromServer)

        Mockito.`when`(chessTaskApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.just(dummySuccessResponse).toObservable())

        Mockito.`when`(loggedUserProvider.getLoggedUserTokens())
            .thenReturn(UserTokens("123"))

        val chessTaskRepository = ChessTasksRepository(chessTaskApi)
        val reducer = TasksListReducer(chessTaskRepository, loggedUserProvider)

        val tasksListStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.getTaskById("1")
        assertNotNull(tasksListStateObserver.values().last().loadedTask)
    }

    @Test
    fun `get task by difficulty return task`() {
        val dummySuccessResponse = Response(200, "Success", dummyTaskFromServer)

        Mockito.`when`(chessTaskApi.getTaskByDifficulty(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.just(dummySuccessResponse).toObservable())

        Mockito.`when`(loggedUserProvider.getLoggedUserTokens())
            .thenReturn(UserTokens("123"))

        val chessTaskRepository = ChessTasksRepository(chessTaskApi)
        val reducer = TasksListReducer(chessTaskRepository, loggedUserProvider)

        val tasksListStateObserver = reducer.updateState
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.getTaskByDifficulty(ChessTaskDifficulty.EASY)
        assertNotNull(tasksListStateObserver.values().last().loadedTask)
    }

    @Test
    fun `logout action leads to redirection to another screen`() {
        val chessTaskRepository = ChessTasksRepository(chessTaskApi)
        val reducer = TasksListReducer(chessTaskRepository, loggedUserProvider)

        val tasksListNewsObserver = reducer.updateNews
            .test()
            .assertValueCount(0)
            .assertNotComplete()

        reducer.logout()
        assertEquals(NewsMessageId.LOGOUT, tasksListNewsObserver.values().last().id)
    }
}