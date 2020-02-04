package com.otus.homework.chessclient.core.presenters

import com.otus.homework.chessclient.core.views.BaseView

interface BasePresenter {
    fun attachView(view:BaseView)
    fun detachView()
}