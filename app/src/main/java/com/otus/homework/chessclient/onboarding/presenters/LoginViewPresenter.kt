package com.otus.homework.chessclient.onboarding.presenters

import com.otus.homework.chessclient.core.views.BaseView
import com.otus.homework.chessclient.onboarding.model.LoginState
import com.otus.homework.chessclient.onboarding.reducer.LoginReducer
import com.otus.homework.chessclient.onboarding.views.ILoginView
import com.otus.homework.model.user.UserShortData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class LoginViewPresenter(private val reducer: LoginReducer) : LoginPresenter {
    private var presenterView:ILoginView? = null
    private val disposeContainer:CompositeDisposable = CompositeDisposable()

    override fun attachView(view: BaseView) {
        presenterView = view as? ILoginView
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

        presenterView?.loginClick()?.subscribe {
            renderState(reducer.tryToLogin())
        }?.addTo(disposeContainer)

        presenterView?.registerClick()?.subscribe {
            renderState(reducer.register())
        }?.addTo(disposeContainer)

        reducer.updateState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                renderState(it)
            }
            ?.addTo(disposeContainer)

        reducer.updateNews
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.displayMessage(it)
            }
            ?.addTo(disposeContainer)

        reducer.updateDestination
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenterView?.navigateTo(it)
            }
            ?.addTo(disposeContainer)
    }

    private fun renderState(newState:LoginState) {
        presenterView?.setLoading(newState.loadingActive)
        presenterView?.setLoginButtonEnabled(newState.loginButtonEnabled)
        presenterView?.setRegisterButtonEnabled(newState.registrationButtonEnabled)
        presenterView?.setLoginTextEnabled(newState.loginTextFieldEnabled)
        presenterView?.setPasswordTextEnabled(newState.passwordTextField)
    }
}