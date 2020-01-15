package com.otus.homework.chessclient.onboarding.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.otus.homework.chessclient.R
import com.otus.homework.chessclient.onboarding.presenters.IRegistrationPresenter
import com.otus.homework.model.enums.AppScreens
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_registration.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment(), IRegisterView, KodeinAware {
    override val kodein: Kodein by kodein()

    private val presenter: IRegistrationPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun setRegisterButtonEnabled(isEnabled: Boolean) {
        activity?.runOnUiThread {
            registerButton.isEnabled = isEnabled
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

    override fun registerClick(): Observable<Any>  = RxView.clicks(registerButton)

    override fun credentialsChange(): Observable<List<String>> = Observable.combineLatest<String, String, List<String>> (
        emailChange(),
        passwordChange(),
        BiFunction  { login:String, password:String ->
            listOf(login, password)
        }
    )

    override fun displayMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(destination: AppScreens) {
        when(destination) {
            AppScreens.LOGIN_SCREEN -> findNavController().popBackStack()
            AppScreens.MAIN_SCREEN -> findNavController().navigate(R.id.action_registerFragment_to_tasksListFragment)
            else -> displayMessage(resources.getString(R.string.unknown_screen_error))
        }
     }

    private fun emailChange(): Observable<String> = RxTextView.textChanges(emailText)
        .map { emailValue -> emailValue.toString() }
        .debounce(500, TimeUnit.MICROSECONDS)
    private fun passwordChange(): Observable<String> = RxTextView.textChanges(passwordText)
        .map { emailValue -> emailValue.toString() }
        .debounce(500, TimeUnit.MICROSECONDS)
}