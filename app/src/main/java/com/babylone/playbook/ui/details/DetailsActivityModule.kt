package com.babylone.playbook.ui.details

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.details.DetailsUseCase
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {

    @Provides
    fun providePresenter(
        detailsUseCase: DetailsUseCase,
        schedulers: SchedulerProvider
    ): DetailsActivityPresenter {
        return DetailsActivityPresenter(detailsUseCase, schedulers)
    }

}
