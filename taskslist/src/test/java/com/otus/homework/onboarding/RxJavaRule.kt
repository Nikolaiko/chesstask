package com.otus.homework.onboarding

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.ExternalResource

class RxJavaRule : ExternalResource (){
    override fun before() {
        super.before()
        replaceShedulers()
    }

    private fun replaceShedulers() {
        RxAndroidPlugins.initMainThreadScheduler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    override fun after() {
        super.after()
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}