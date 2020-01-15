package com.otus.homework.chessclient.core.presenters

import com.otus.homework.chessclient.core.views.IView

interface IPresenter {
    fun attachView(view:IView)
    fun detachView()
}