package com.otus.homework.app

import android.widget.Button
import com.example.core.model.enums.ChessFigureColor
import com.example.core.model.enums.ChessFigureType
import com.example.core.model.task.*
import com.otus.homework.ChessApplication
import com.otus.homework.chesstask.ChessTaskActivity
import com.otus.homework.chesstask.views.ChessTaskFragment
import com.otus.homework.main.MainActivity
import com.otus.homework.taskslist.TasksListActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23], application = ChessApplication::class)
class ChessTaskActivityTest {

    @Before
    fun prepareTask() {
        ChessTaskActivity.selectedTask = ChessTask("id",
            buildStartingPosition(),
            ChessFigureColor.w,
            buildPgn())
    }

    @Test
    fun chessTaskScreen_backButtonTap_chessTasksListActivityStart() {
        val chessTaskActivity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

        val fragment = ChessTaskFragment()
        val manager = chessTaskActivity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(fragment, null)
        transaction.commit()

        val backButton = chessTaskActivity.findViewById<Button>(com.otus.homework.chesstask.R.id.backButton)
        assertNotNull(backButton)
        backButton.performClick()

        val intent = Shadows.shadowOf(chessTaskActivity).peekNextStartedActivity()
        println("${TasksListActivity::class.simpleName}, ${intent.component?.className}")
        assertEquals(TasksListActivity::class.simpleName, intent.component?.className)
    }

    private fun buildStartingPosition(): List<ChessFigure> {
        val firstFigurePosition = FigurePosition(1, 1)
        val secondFigurePosition = FigurePosition(1, 5)
        val thirdFigurePosition = FigurePosition(3, 3)

        val firstFigure = ChessFigure(
            ChessFigureType.rock,
            ChessFigureColor.w,
            firstFigurePosition
        )

        val secondFigure = ChessFigure(
            ChessFigureType.knight,
            ChessFigureColor.b,
            secondFigurePosition
        )

        val thirdFigure = ChessFigure(
            ChessFigureType.queen,
            ChessFigureColor.w,
            thirdFigurePosition
        )
        return listOf(firstFigure, secondFigure, thirdFigure)
    }

    private fun buildPgn(): List<PgnMovePair> {
        val mutable = mutableListOf<PgnMovePair>()
        var pair = PgnMovePair(
            PgnMove(
                ChessFigureType.queen,
                ChessFigureColor.w,
                FigurePosition(3, 5),
                null
            ),
            PgnMove(
                ChessFigureType.knight,
                ChessFigureColor.b,
                FigurePosition(3,4),
                null
            )
        )
        mutable.add(pair)

        pair = PgnMovePair(
            PgnMove(
                ChessFigureType.rock,
                ChessFigureColor.w,
                FigurePosition(1, 5),
                null
            )
        )
        mutable.add(pair)
        return mutable.toList()
    }
}