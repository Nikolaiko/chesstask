package com.otus.homework.chesstask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.otus.homework.chesstask.R
import kotlinx.android.synthetic.main.dialog_win_task.*

class WinDialog : DialogFragment() {
    var restartCallback: (()->Unit)? = null
    var exitCallback: (()->Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.dialog_win_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restartButton.setOnClickListener {
            restartCallback?.invoke()
        }

        exitButton.setOnClickListener {
            exitCallback?.invoke()
        }
    }
}