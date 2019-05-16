package com.babylone.playbook.ui.main

import android.annotation.SuppressLint
import com.babylone.playbook.core.Resource
import com.babylone.playbook.core.mvp.MviPresenter
import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.fetcher.FetcherUseCase
import com.babylone.playbook.domain.post.PostUseCase
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single

class MainActivityPresenter(
        private val mainNavigator: MainNavigator,
        private val mainUseCase: PostUseCase,
        private val fetcherUseCase: FetcherUseCase,
        private val schedulers: SchedulerProvider
) : MviPresenter<MainActivityPresenter.Action, MainViewPartialState, MainActivityViewState, MainActivityView>() {

    sealed class Action {

        object ReloadItemsAction : Action()

    }

    private fun loadPostTitlesTransformer(): ObservableTransformer<Action.ReloadItemsAction, MainViewPartialState.Result> {
        return ObservableTransformer { action: Observable<Action.ReloadItemsAction> ->
            action.switchMapSingle {
                fetcherUseCase.fetched()
                        .map { it }
                        .flatMap { if (it) Single.just(Unit) else fetcherUseCase.prefetch() }
                        .subscribeOn(schedulers.io())
            }.switchMap {
                mainUseCase.getTitles()
                        .toObservable()
                        .map { posts -> posts.map(MainItemFactory::fromPost) }
                        .map { Resource.success(it) }
                        .onErrorReturn { error -> Resource.error(error) }
                        .startWith(Resource.loading())
                        .map { MainViewPartialState.Result(it) }
                        .subscribeOn(schedulers.io())
            }
        }
    }

    private fun mainTransformer(): ObservableTransformer<Action, MainViewPartialState> {
        return ObservableTransformer { actions ->
            actions.ofType(Action.ReloadItemsAction::class.java)
                    .compose(loadPostTitlesTransformer())
        }
    }

    @SuppressLint("CheckResult")
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        observeAction()
                .compose(mainTransformer())
                .compose(bindObservableLifecycleTransformer())
                .scan(MainActivityViewState.init(), mainReducer())
                .replay(1)
                .autoConnect(0)
                .distinctUntilChanged()
                .observeOn(schedulers.mainThread())
                .compose(bindTestSubject())
                .subscribe { viewState?.renderView(it) }
    }

    private fun mainReducer(): (MainActivityViewState, MainViewPartialState) -> MainActivityViewState {
        return { accumulator, changes ->
            when (changes) {
                is MainViewPartialState.Result -> {
                    accumulator.copy(resource = changes.data)
                }
            }
        }
    }

    fun reloadScreen() {
        onAction(Action.ReloadItemsAction)
    }

    fun onPostClicked(postId: Int) {
        mainNavigator.showPostDetails(postId)
    }

}