package com.otus.homework.onboarding.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.core_api.mediator.AppWithContext
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.otus.homework.onboarding.R
import com.otus.homework.onboarding.di.DaggerLoginComponent
import com.otus.homework.onboarding.model.News
import com.otus.homework.onboarding.model.enums.NewsMessageId
import com.otus.homework.onboarding.presenters.ILoginPresenter
import com.otus.homework.onboarding.model.enums.OnBoardingScreens
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginFragment : Fragment(), ILoginView {
    @Inject
    lateinit var presenter:ILoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerLoginComponent
            .builder()
            .providerFacade((activity!!.application as AppWithContext).getFacade())
            .build()
            .inject(this)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun setRegisterButtonEnabled(isEnabled: Boolean) {
        activity?.runOnUiThread {
            registerButton.isEnabled = isEnabled
        }
    }

    override fun setLoginButtonEnabled(isEnabled: Boolean) {
        activity?.runOnUiThread {
            loginButton.isEnabled = isEnabled
        }
    }

    override fun setPasswordTextEnabled(isEnabled: Boolean) {
        activity?.runOnUiThread {
            passwordText.isEnabled = isEnabled
        }
    }

    override fun setLoginTextEnabled(isEnabled: Boolean) {
        activity?.runOnUiThread {
            emailText.isEnabled = isEnabled
        }
    }

    override fun setLoading(isLoading: Boolean) {
        activity?.runOnUiThread {
            when(isLoading) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun registerClick(): Observable<Any> = RxView.clicks(registerButton)

    override fun loginClick(): Observable<Any> = RxView.clicks(loginButton)

    override fun credentialsChange():Observable<List<String>> = Observable.combineLatest<String, String, List<String>> (
            emailChange(),
            passwordChange(),
            BiFunction  { login:String, password:String ->
                listOf(login, password)
            }
        )

    override fun displayMessage(newsMessage: News) {
        activity?.runOnUiThread {
            Toast.makeText(context!!, convertNewsToString(newsMessage), Toast.LENGTH_SHORT).show()
        }
    }

    override fun navigateTo(destination: OnBoardingScreens) {
        when(destination) {
            OnBoardingScreens.REGISTER_SCREEN -> NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment)
            //OnBoardingScreens.MAIN_SCREEN -> NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_tasksListFragment)
            else -> displayMessage(News(NewsMessageId.UNKNOWN_DESTINATION))
        }
    }

    private fun emailChange():Observable<String> = RxTextView.textChanges(emailText)
        .map { emailValue -> emailValue.toString() }
        .debounce(500, TimeUnit.MICROSECONDS)
    private fun passwordChange():Observable<String> = RxTextView.textChanges(passwordText)
        .map { emailValue -> emailValue.toString() }
        .debounce(500, TimeUnit.MICROSECONDS)

    private fun convertNewsToString(news:News):String = when(news.id) {
        NewsMessageId.UNKNOWN_DESTINATION -> resources.getString(R.string.unknown_screen_error)
        NewsMessageId.NULL_BODY_MESSAGE -> resources.getString(R.string.null_body_error)
        NewsMessageId.REQUEST_STATUS_ERROR -> resources.getString(R.string.request_status_error, news.message)
        NewsMessageId.EXCEPTION_LOGIN_REQUEST -> resources.getString(R.string.exception_login_request_error, news.message)
        NewsMessageId.EXCEPTION_REGISTRATION_REQUEST -> resources.getString(R.string.exception_registration_request_error, news.message)
    }
}