package com.otus.homework.chessclient.onboarding.views

import com.otus.homework.chessclient.core.views.IView
import io.reactivex.Observable

interface IRegisterView : IView {
    fun setBackButtonEnabled(isEnabled:Boolean)
    fun setRegisterButtonEnabled(isEnabled:Boolean)
    fun setPasswordTextEnabled(isEnabled:Boolean)
    fun setLoginTextEnabled(isEnabled:Boolean)
    fun setLoading(isLoading:Boolean)

    fun registerClick(): Observable<Any>
    fun backClick(): Observable<Any>
    fun credentialsChange(): Observable<List<String>>
}