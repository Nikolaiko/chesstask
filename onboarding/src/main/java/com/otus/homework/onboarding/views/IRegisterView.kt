package com.otus.homework.onboarding.views

import io.reactivex.Observable

interface IRegisterView : BaseView {
    fun setBackButtonEnabled(isEnabled:Boolean)
    fun setRegisterButtonEnabled(isEnabled:Boolean)
    fun setPasswordTextEnabled(isEnabled:Boolean)
    fun setLoginTextEnabled(isEnabled:Boolean)
    fun setLoading(isLoading:Boolean)

    fun registerClick(): Observable<Any>
    fun backClick(): Observable<Any>
    fun credentialsChange(): Observable<List<String>>
}
