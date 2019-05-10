package com.babylone.playbook.ui.splash

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.fetcher.FetcherUseCase
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun providePresenter(loaderUseCase: FetcherUseCase, schedulers: SchedulerProvider): SplashPresenter {
        return SplashPresenter(loaderUseCase, schedulers)
    }

}