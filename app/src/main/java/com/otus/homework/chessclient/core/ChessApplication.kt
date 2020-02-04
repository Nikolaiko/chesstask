package com.otus.homework.chessclient.core

import android.app.Application
import com.otus.homework.chessclient.onboarding.presenters.LoginPresenter
import com.otus.homework.chessclient.onboarding.presenters.RegistrationPresenter
import com.otus.homework.chessclient.onboarding.presenters.LoginViewPresenter
import com.otus.homework.chessclient.onboarding.presenters.RegistrationViewPresenter
import com.otus.homework.chessclient.onboarding.reducer.LoginReducer
import com.otus.homework.chessclient.onboarding.reducer.RegistrationReducer
import com.otus.homework.chessclient.onboarding.reducer.UserLoginReducer
import com.otus.homework.chessclient.onboarding.reducer.UserRegistrationReducer
import com.otus.homework.network.OnBoardingApi
import com.otus.homework.network.RetrofitBuilder
import com.otus.homework.network.interfaces.UserApi
import com.otus.homework.network.interfaces.NetworkEngineBuilder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ChessApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        bind<NetworkEngineBuilder>() with singleton { RetrofitBuilder() }
        bind<UserApi>() with singleton { OnBoardingApi(instance()) }

        bind<LoginReducer>() with provider { UserLoginReducer( instance()) }
        bind<LoginPresenter>() with provider { LoginViewPresenter( instance()) }

        bind<RegistrationReducer>() with provider { UserRegistrationReducer(instance()) }
        bind<RegistrationPresenter>() with provider { RegistrationViewPresenter(instance()) }
    }
}