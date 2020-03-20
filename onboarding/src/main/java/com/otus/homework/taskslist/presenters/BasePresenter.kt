package com.otus.homework.taskslist.presenters

import com.otus.homework.onboarding.views.BaseView

interface BasePresenter {
    fun attachView(view: BaseView)
    fun detachView()
}
