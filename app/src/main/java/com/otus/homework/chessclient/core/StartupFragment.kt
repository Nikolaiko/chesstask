package com.otus.homework.chessclient.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.otus.homework.chessclient.R
import com.otus.homework.model.enums.ChessTaskDifficulty
import com.otus.homework.model.user.UserShortData
import com.otus.homework.network.interfaces.IOnBoardingApi
import kotlinx.android.synthetic.main.fragment_startup.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.random.Random

class StartupFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()

    private val network:IOnBoardingApi by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_startup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}