package com.babylone.playbook.ui.splash

import android.annotation.SuppressLint
import com.babylone.playbook.core.Resource
import com.babylone.playbook.core.mvp.MviPresenter
import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.fetcher.FetcherUseCase
import io.reactivex.ObservableTransformer

class SplashPresenter(
    private val loaderUseCase: FetcherUseCase,
    private val schedulers: SchedulerProvider
) : MviPresenter<SplashAction, SplashPartialViewState, SplashViewState, SplashView>() {

    private fun splashTransformer(): ObservableTransformer<SplashAction, SplashPartialViewState> =
        ObservableTransformer { action ->
            action.ofType(SplashAction.Initialize::class.java).compose(prefetchTransformer())
        }

    private fun prefetchTransformer(): ObservableTransformer<SplashAction.Initialize, SplashPartialViewState> =
        ObservableTransformer { action ->
            action.switchMap {
                loaderUseCase.prefetch()
                    .map { Resource.success(true) }
                    .onErrorReturn { error -> Resource.error(error) }
                    .startWith(Resource.loading())
                    .map { SplashPartialViewState.Result(it) }
                    .subscribeOn(schedulers.io())
            }
        }

    @SuppressLint("CheckResult")
    override fun onFirstViewAttach() {
        observeAction()
            .compose(bindObservableLifecycleTransformer())
            .compose(splashTransformer())
            .scan(SplashViewState.init(), splashReducer())
            .distinctUntilChanged()
            .observeOn(schedulers.mainThread())
            .compose(bindTestSubject())
            .subscribe { viewState?.renderView(it) }
    }

    private fun splashReducer(): (SplashViewState, SplashPartialViewState) -> SplashViewState {
        return { state, changes ->
            when (changes) {
                is SplashPartialViewState.Result -> {
                    state.copy(resource = changes.resource)
                }
            }
        }
    }

    fun initialize() {
        onAction(SplashAction.Initialize)
    }

}