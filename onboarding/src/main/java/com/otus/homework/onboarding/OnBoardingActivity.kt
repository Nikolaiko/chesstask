package com.otus.homework.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OnBoardingActivity : AppCompatActivity() {
    companion object {
        fun startOnBoardingActivity(context: Context) {
            context.startActivity(Intent(context, OnBoardingActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

    }
}
