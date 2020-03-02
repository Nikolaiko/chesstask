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

    @Test
    fun expectedAllTasksResponse_callGetAllTasks_compareResultsToExpectedList() {
        //GIVEN
        val taskId = "id"
        val taskName = "testTask"
        val taskShortData = ChessTaskShortData(taskId, taskName)
        val taskData = ChessTaskShortInfo(taskId, taskName)
        val list = listOf(taskData)
        val allTasksResponse = Response(200, null, listOf(taskShortData))
        val repository = ChessTasksRepository(tasksApi)

        Mockito.`when`(tasksApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.just(allTasksResponse).toObservable())

        //WHEN AND THEN
        repository.getAllTasks(UserTokens(""))
            .test()
            .assertResult(list)
    }

    @Test
    fun wrongResponseFromServer_callGetAllTasks_checkExceptionHappen() {
        //GIVEN
        val allTasksNullResponse = Response<List<ChessTaskShortData>>(200, "Null Body", null)
        val repository = ChessTasksRepository(tasksApi)

        Mockito.`when`(tasksApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.just(allTasksNullResponse).toObservable())

        //WHEN AND THEN
        repository.getAllTasks(UserTokens(""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun mockObjectWithWrongStatusResponse_callGetAllTasks_checkExceptionHappen() {
        //GIVEN
        val repository = ChessTasksRepository(tasksApi)
        Mockito.`when`(tasksApi.getAllTasks(Mockito.anyString()))
            .thenReturn(Single.error<Response<List<ChessTaskShortData>>>(Exception("Error status")).toObservable())

        //WHEN AND THEN
        repository.getAllTasks(UserTokens(""))
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun taskResponseExample_callGetTaskById_compareToExpectedParsedResult() {
        //GIVEN
        val expectedTaskId = "id"
        val expectedFen = "r2qkb1r/pp2nppp/3p4/2pNN1B1/2BnP3/3P4/PPP2PPP/R2bK2R w KQkq - 1 0"
        val expectedPgn = "1. Nf6+ gxf6 2. Bxf7# "

        val responseTask = ChessTaskData(
            expectedTaskId,
            expectedFen,
            expectedPgn
        )
        val parsedTask = ChessTask(
            expectedTaskId,
            getStartingPositions(expectedFen),
            getStartingColor(expectedFen),
            parsePgnString(expectedPgn)
        )
        val idTaskResponse = Response(200,"", responseTask)
        val repository = ChessTasksRepository(tasksApi)

        Mockito.`when`(tasksApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.just(idTaskResponse).toObservable())


        //WHEN AND THEN
        repository.getTaskById(UserTokens(""), expectedTaskId)
            .test()
            .assertResult(parsedTask)
    }

    @Test
    fun nullBodyTaskResponse_callGetTaskById_checkExceptionHappen() {
        //GIVEN
        val expectedTaskId = "id"
        val idTaskNullResponse = Response<ChessTaskData>(200, "Null Body", null)
        val repository = ChessTasksRepository(tasksApi)

        Mockito.`when`(tasksApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.just(idTaskNullResponse).toObservable())

        //WHEN AND THEN
        repository.getTaskById(UserTokens(""), expectedTaskId)
            .test()
            .assertFailure(Exception::class.java)
    }

    @Test
    fun mockApiReturningWrongStatus_callGetTaskById_checkExceptionHappen() {
        //GIVEN
        val expectedTaskId = "id"
        val repository = ChessTasksRepository(tasksApi)

        Mockito.`when`(tasksApi.getTaskById(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Single.error<Response<ChessTaskData>>(Exception("Error status")).toObservable())

        //WHEN AND THEN
        repository.getTaskById(UserTokens(""), expectedTaskId)
            .test()
            .assertFailure(Exception::class.java)
    }
}