package com.otus.homework.chessclient.onboarding.presenters

import com.otus.homework.chessclient.core.views.BaseView
import com.otus.homework.chessclient.onboarding.model.RegistrationState
import com.otus.homework.chessclient.onboarding.reducer.RegistrationReducer
import com.otus.homework.chessclient.onboarding.views.IRegisterView
import com.otus.homework.model.user.UserShortData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class RegistrationViewPresenter(private val reducer: RegistrationReducer) : RegistrationPresenter {
    private var presenterView:IRegisterView? = null
    private val disposeContainer: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: BaseView) {
        presenterView = view as? IRegisterView
        bind()
    }

    override fun detachView() {
        presenterView = null
        disposeContainer.clear()
        reducer.clearDisposables()
    }

    private fun bind() {
        presenterView?.credentialsChange()?.subscribe {
            renderState(reducer.credentialsChange(UserShortData(it[0], it[1])))
        }?.addTo(disposeContainer)

        presenterView?.registerClick()?.subscribe {
            renderState(reducer.tryToRegister())
        }?.addTo(disposeContainer)

        presenterView?.backClick()?.subscribe {
            reducer.goToPreviousScreen()
        }?.addTo(disposeContainer)


        reducer.updateState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                renderState(it)
            }
            .addTo(disposeContainer)

        reducer.updateNews
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.displayMessage(it)
            }
            .addTo(disposeContainer)

        reducer.updateDestination
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.navigateTo(it)
            }
            .addTo(disposeContainer)
    }

    private fun renderState(newState: RegistrationState) {
        presenterView?.setLoading(newState.loadingActive)
        presenterView?.setRegisterButtonEnabled(newState.registrationButtonEnabled)
        presenterView?.setLoginTextEnabled(newState.loginTextFieldEnabled)
        presenterView?.setPasswordTextEnabled(newState.passwordTextField)
        presenterView?.setBackButtonEnabled(newState.backButtonEnabled)
    }
}