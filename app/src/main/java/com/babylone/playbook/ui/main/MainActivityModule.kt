package com.babylone.playbook.ui.main

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.post.PostUseCase
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideNavigator(context: MainActivity): MainNavigator {
        return MainNavigatorImpl(context)
    }

    @Provides
    fun providePresenter(
        navigator: MainNavigator,
        mainUseCase: PostUseCase,
        schedulers: SchedulerProvider
    ): MainActivityPresenter {
        return MainActivityPresenter(navigator, mainUseCase, schedulers)
    }
}
