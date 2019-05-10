package com.babylone.playbook.di

import com.babylone.playbook.ui.details.DetailsActivity
import com.babylone.playbook.ui.main.MainActivity
import com.babylone.playbook.ui.main.MainActivityModule
import com.babylone.playbook.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailsActivity(): DetailsActivity

}