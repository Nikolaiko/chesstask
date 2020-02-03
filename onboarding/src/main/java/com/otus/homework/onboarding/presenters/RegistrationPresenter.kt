package com.otus.homework.onboarding.presenters

import com.example.core.model.UserProfile
import com.otus.homework.onboarding.views.IView
import com.otus.homework.onboarding.model.RegistrationState
import com.otus.homework.onboarding.reducer.IRegistrationReducer
import com.otus.homework.onboarding.views.IRegisterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RegistrationPresenter @Inject constructor(private val reducer: IRegistrationReducer) : IRegistrationPresenter {
    private var presenterView:IRegisterView? = null
    private val disposeContainer: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: IView) {
        presenterView = view as? IRegisterView
        bind()
    }

    override fun detachView() {
        presenterView = null
        disposeContainer.clear()
        reducer.clearDisposables()
    }

    private fun bind() {
        val credentialsObserver = presenterView?.credentialsChange()?.subscribe {
            renderState(reducer.credentialsChange(UserProfile(it[0], it[1])))
        }
        if (credentialsObserver != null) {
            disposeContainer.add(credentialsObserver)
        }

        val registerClickObserver = presenterView?.registerClick()?.subscribe {
            renderState(reducer.tryToRegister())
        }
        if (registerClickObserver != null) {
            disposeContainer.add(registerClickObserver)
        }

        val backClickObserver = presenterView?.backClick()?.subscribe {
            reducer.goToPreviousScreen()
        }
        if (backClickObserver != null) {
            disposeContainer.add(backClickObserver)
        }

        disposeContainer.add(reducer.updateState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                renderState(it)
            })

        disposeContainer.add(reducer.updateNews
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.displayMessage(it)
            })

        disposeContainer.add(reducer.updateDestination
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.navigateTo(it)
            })
    }

    private fun renderState(newState: RegistrationState) {
        presenterView?.setLoading(newState.loadingActive)
        presenterView?.setRegisterButtonEnabled(newState.registrationButtonEnabled)
        presenterView?.setLoginTextEnabled(newState.loginTextFieldEnabled)
        presenterView?.setPasswordTextEnabled(newState.passwordTextField)
        presenterView?.setBackButtonEnabled(newState.backButtonEnabled)
    }
}