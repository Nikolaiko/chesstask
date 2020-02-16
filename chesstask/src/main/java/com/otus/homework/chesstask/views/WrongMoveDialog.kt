package com.otus.homework.chesstask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.otus.homework.chesstask.R
import kotlinx.android.synthetic.main.dialog_wrong_move.*

class WrongMoveDialog : DialogFragment() {
    var undoCallback: (()->Unit)? = null
    var restartCallback: (()->Unit)? = null
    var exitCallback: (()->Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.dialog_wrong_move, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        undoButton.setOnClickListener {
            undoCallback?.invoke()
        }

        restartButton.setOnClickListener {
            restartCallback?.invoke()
        }

        exitButton.setOnClickListener {
            exitCallback?.invoke()
        }
    }
}