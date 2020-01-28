package com.otus.homework.onboarding.views

import io.reactivex.Observable

interface ILoginView : IView {
    fun setRegisterButtonEnabled(isEnabled:Boolean)
    fun setLoginButtonEnabled(isEnabled:Boolean)
    fun setPasswordTextEnabled(isEnabled:Boolean)
    fun setLoginTextEnabled(isEnabled:Boolean)
    fun setLoading(isLoading:Boolean)

    fun registerClick():Observable<Any>
    fun loginClick():Observable<Any>
    fun credentialsChange():Observable<List<String>>
}