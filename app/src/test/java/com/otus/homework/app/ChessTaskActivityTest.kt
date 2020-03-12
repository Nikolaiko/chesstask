package com.otus.homework.app

import android.widget.ImageView
import com.otus.homework.ChessApplication
import com.otus.homework.chesstask.ChessTaskActivity
import com.otus.homework.chesstask.MAX_BOARD_INDEX
import junit.framework.TestCase.assertNotNull
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [24],
    application = ChessApplication::class
)
class ChessTaskActivityTest {
    companion object{
        @BeforeClass
        @JvmStatic
        fun prepareTask() {
            ChessTaskActivity.selectedTask = buildChessTask()
        }
    }

    @Test
    fun `should have a board 8x8 with cells`() {
        val chessTaskActivity = Robolectric.buildActivity(ChessTaskActivity::class.java)
            .create()
            .resume()
            .get()

        for (i in 0..MAX_BOARD_INDEX) {
            for (j in 0..MAX_BOARD_INDEX) {
                val cell = chessTaskActivity.findViewById<ImageView>(
                    chessTaskActivity.resources.getIdentifier(
                    "cell$i$j",
                    "id", chessTaskActivity.applicationContext.packageName
                    )
                )
                assertNotNull(cell)
            }
        }
    }
}