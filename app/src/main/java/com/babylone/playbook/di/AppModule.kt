package com.babylone.playbook.di

import android.content.Context
import com.babylone.playbook.PlaybookApp
import com.babylone.playbook.core.DefaultImageLoader
import com.babylone.playbook.core.ImageLoaderProxy
import com.babylone.playbook.core.mvp.DefaultSchedulersProvider
import com.babylone.playbook.core.mvp.SchedulerProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Module(includes = [AndroidInjectionModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: PlaybookApp): Context = app

    @Provides
    fun providesSchedulesFactory(): SchedulerProvider = DefaultSchedulersProvider

    @Provides
    fun provideGlideRequest(context: Context) = Glide.with(context)

    @Provides
    fun provideImageLoaderProxy(requestManager: RequestManager): ImageLoaderProxy = DefaultImageLoader(requestManager)
}