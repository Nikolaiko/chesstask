package com.otus.homework.onboarding.presenters

import com.otus.homework.onboarding.views.IView

interface IPresenter {
    fun attachView(view:IView)
    fun detachView()
}