package com.otus.homework.storage.implementations

import com.example.core.model.task.ChessTask
import com.example.core.model.task.ChessTaskShortInfo
import com.example.core.model.user.UserTokens
import com.otus.homework.network.interfaces.ChessTasksApi
import com.otus.homework.network.model.chesstasks.ChessTaskData
import com.otus.homework.network.model.chesstasks.ChessTaskShortData
import com.otus.homework.network.model.responses.Response
import com.otus.homework.storage.RxJavaRule
import com.otus.homework.storage.getStartingColor
import com.otus.homework.storage.getStartingPositions
import com.otus.homework.storage.parsePgnString
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ChessTasksRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var tasksApi: ChessTasksApi

    private lateinit var repository:ChessTasksRepository

    private val taskId = "id"
    private val taskName = "testTask"
    private val taskData = ChessTaskShortInfo(taskId, taskName)
    private val list = listOf(taskData)

    private val taskShortData = ChessTaskShortData(taskId, taskName)
    private val allTasksResponse = Response(200, null, listOf(taskShortData))
    private val allTasksNullResponse = Response<List<ChessTaskShortData>>(200, "Null Body", null)
    private val idTaskNullResponse = Response<ChessTaskData>(200, "Null Body", null)

    private val taskFen = "r2qkb1r/pp2nppp/3p4/2pNN1B1/2BnP3/3P4/PPP2PPP/R2bK2R w KQkq - 1 0"
    private val taskPgn = "1. Nf6+ gxf6 2. Bxf7# "
    private val responseTask = ChessTaskData(
        taskId,
        taskFen,
        taskPgn
    )
    private val idTaskResponse = Response(200,"", responseTask)
    private val parsedTask = ChessTask(
        taskId,
        getStartingPositions(taskFen),
        getStartingColor(taskFen),
        parsePgnString(taskPgn)
    )

    @Before
    fun setUp() {
        repository = ChessTasksRepository(tasksApi)
    }

    @Test
    fun getAllTasks() {
        Mockito.`when`(tasksApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.just(allTasksResponse).toObservable())

        repository.getAllTasks(UserTokens(""))
            .test()
            .assertResult(list)
    }

    @Test
    fun getAllTasksNullBody() {
        Mockito.`when`(tasksApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.just(allTasksNullResponse).toObservable())

        repository.getAllTasks(UserTokens(""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun getAllTasksStatusError() {
        Mockito.`when`(tasksApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.error<Response<List<ChessTaskShortData>>>(Exception("Error status")).toObservable())

        repository.getAllTasks(UserTokens(""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun getTaskById() {
        Mockito.`when`(tasksApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.just(idTaskResponse).toObservable())

        repository.getTaskById(UserTokens(""), taskId)
            .test()
            .assertResult(parsedTask)
    }

    @Test
    fun getTaskByIdNullBody() {
        Mockito.`when`(tasksApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.just(idTaskNullResponse).toObservable())

        repository.getTaskById(UserTokens(""), taskId)
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun getTaskByIdStatusError() {
        Mockito.`when`(tasksApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.error<Response<ChessTaskData>>(Exception("Error status")).toObservable())

        repository.getTaskById(UserTokens(""), taskId)
            .test()
            .assertFailure(Exception::class.java)
    }
}