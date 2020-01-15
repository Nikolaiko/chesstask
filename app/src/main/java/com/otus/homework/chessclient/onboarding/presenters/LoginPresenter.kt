package com.otus.homework.chessclient.onboarding.presenters

import com.otus.homework.chessclient.core.views.IView
import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.reducer.ILoginReducer
import com.otus.homework.chessclient.onboarding.views.ILoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class LoginPresenter(private val reducer: ILoginReducer) : ILoginPresenter {
    private var presenterView:ILoginView? = null
    private val disposeContainer:CompositeDisposable = CompositeDisposable()

    override fun attachView(view: IView) {
        presenterView = view as? ILoginView
        bind()
    }

    override fun detachView() {
        presenterView = null
        disposeContainer.clear()
        reducer.clearDisposables()
    }

    private fun bind() {
        val credentialsObserver = presenterView?.credentialsChange()?.subscribe {
            renderState(reducer.credentialsChange(it))
        }
        if (credentialsObserver != null) {
            disposeContainer.add(credentialsObserver)
        }

        val loginClickObserver = presenterView?.loginClick()?.subscribe {
            renderState(reducer.tryToLogin())
        }
        if (loginClickObserver != null) {
            disposeContainer.add(loginClickObserver)
        }

        val registerClickObserver = presenterView?.registerClick()?.subscribe {
            renderState(reducer.register())
        }
        if (registerClickObserver != null) {
            disposeContainer.add(registerClickObserver)
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

    private fun renderState(newState:LoginState) {
        presenterView?.setLoading(newState.loadingActive)
        presenterView?.setLoginButtonEnabled(newState.loginButtonEnabled)
        presenterView?.setRegisterButtonEnabled(newState.registrationButtonEnabled)
        presenterView?.setLoginTextEnabled(newState.loginTextFieldEnabled)
        presenterView?.setPasswordTextEnabled(newState.passwordTextField)
    }
}