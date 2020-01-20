package com.otus.homework.chessclient.core

import android.app.Application
import com.otus.homework.chessclient.core.di.DaggerCoreComponent
import com.otus.homework.chessclient.onboarding.presenters.ILoginPresenter
import com.otus.homework.chessclient.onboarding.presenters.IRegistrationPresenter
import com.otus.homework.chessclient.onboarding.presenters.LoginPresenter
import com.otus.homework.chessclient.onboarding.presenters.RegistrationPresenter
import com.otus.homework.chessclient.onboarding.reducer.ILoginReducer
import com.otus.homework.chessclient.onboarding.reducer.IRegistrationReducer
import com.otus.homework.chessclient.onboarding.reducer.LoginReducer
import com.otus.homework.chessclient.onboarding.reducer.RegistrationReducer
import com.otus.homework.network.OnBoardingApi
import com.otus.homework.network.RetrofitBuilder
import com.otus.homework.network.interfaces.IOnBoardingApi
import com.otus.homework.network.interfaces.IRetrofitBuilder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ChessApplication : Application() {
    companion object{
        val appComponent = DaggerCoreComponent.builder().build()
    }


    override fun onCreate() {
        super.onCreate()
    }


}