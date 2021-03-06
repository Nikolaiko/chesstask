package com.otus.homework.taskslist.presenters

import com.example.core.model.user.UserProfile
import com.otus.homework.onboarding.views.BaseView
import com.otus.homework.onboarding.model.LoginState
import com.otus.homework.onboarding.reducer.UserLoginReducer
import com.otus.homework.onboarding.views.ILoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val reducer: UserLoginReducer
) : UserLoginPresenter {
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
        val credentialsObserver = presenterView?.credentialsChange()?.subscribe {
            reducer.credentialsChange(
                UserProfile(
                    it[0],
                    it[1]
                )
            )
        }
        if (credentialsObserver != null) {
            disposeContainer.add(credentialsObserver)
        }

        val loginClickObserver = presenterView?.loginClick()?.subscribe {
            reducer.tryToLogin()
        }
        if (loginClickObserver != null) {
            disposeContainer.add(loginClickObserver)
        }

        val registerClickObserver = presenterView?.registerClick()?.subscribe {
            reducer.register()
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
