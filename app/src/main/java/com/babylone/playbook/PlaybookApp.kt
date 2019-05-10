package com.babylone.playbook

import android.app.Activity
import android.app.Application
import android.util.Log
import com.babylone.playbook.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject


class PlaybookApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setupRxJavaErrorHandler()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun setupRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                Log.d("RxJavaPlugins", "on UndeliverableException + ${it.message}")
                return@setErrorHandler
            }
            Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), it)
        }
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityInjector
    }

}