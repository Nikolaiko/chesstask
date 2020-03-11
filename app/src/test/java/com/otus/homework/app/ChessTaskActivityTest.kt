package com.otus.homework.app

import android.view.LayoutInflater
import com.otus.homework.ChessApplication
import com.otus.homework.app.shadow.ChessTaskPresenterShadow
import com.otus.homework.chesstask.ChessTaskActivity
import com.otus.homework.chesstask.views.ChessTaskFragment
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [24],
    application = ChessApplication::class,
    shadows = [ChessTaskPresenterShadow::class]
)
class ChessTaskActivityTest {
    companion object{
        @BeforeClass
        @JvmStatic
        fun prepareTask() {
            println("Preparing Task")
            ChessTaskActivity.selectedTask = buildChessTask()
        }
    }

    @Test
    fun chessTaskScreen_logoutButtonTap_onBoardingActivityStart() {
        val chessTaskActivity = Robolectric.buildActivity(ChessTaskActivity::class.java)
            .create()
            .resume()
            .get()
        println(ChessTaskActivity.selectedTask)
        println(ChessTaskPresenterShadow.actualTask)
        assertEquals(ChessTaskPresenterShadow.actualTask, ChessTaskActivity.selectedTask)
    }
}