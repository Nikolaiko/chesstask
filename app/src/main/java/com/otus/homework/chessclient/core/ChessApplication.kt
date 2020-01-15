package com.otus.homework.chessclient.core

import android.app.Application
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

class ChessApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        bind<IRetrofitBuilder>() with singleton { RetrofitBuilder() }
        bind<IOnBoardingApi>() with singleton { OnBoardingApi(instance()) }

        bind<ILoginReducer>() with provider { LoginReducer( instance()) }
        bind<ILoginPresenter>() with provider { LoginPresenter( instance()) }

        bind<IRegistrationReducer>() with provider { RegistrationReducer(instance()) }
        bind<IRegistrationPresenter>() with provider { RegistrationPresenter(instance()) }
    }
}