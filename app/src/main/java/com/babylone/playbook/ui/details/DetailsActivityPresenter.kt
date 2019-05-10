package com.babylone.playbook.ui.details

import android.annotation.SuppressLint
import com.babylone.playbook.core.Resource
import com.babylone.playbook.core.mvp.MviPresenter
import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.details.DetailsUseCase
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class DetailsActivityPresenter(
    private val detailsUseCase: DetailsUseCase,
    private val schedulers: SchedulerProvider
) : MviPresenter<DetailsActivityPresenter.Action, DetailsPartialViewState, DetailsViewState, DetailsView>() {

    sealed class Action {
        data class OpenPostDetails(val postId: Int) : Action()
    }

    private fun transformer(): ObservableTransformer<Action, DetailsPartialViewState> {
        return ObservableTransformer { actions ->
            actions.ofType(Action.OpenPostDetails::class.java)
                .compose(loadPostDetails())
        }
    }

    private fun loadPostDetails(): ObservableTransformer<Action.OpenPostDetails, DetailsPartialViewState.Result> {
        return ObservableTransformer { actions: Observable<Action.OpenPostDetails> ->
            actions.switchMap {
                detailsUseCase.getPostDetails(it.postId)
                    .toObservable()
                    .map(DetailPostItemFactory::create)
                    .map { Resource.success(it) }
                    .onErrorReturn { error -> Resource.error(error) }
                    .startWith(Resource.loading())
                    .map { DetailsPartialViewState.Result(it) }
                    .subscribeOn(schedulers.io())
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        observeAction()
            .compose(bindObservableLifecycleTransformer())
            .compose(transformer())
            .scan(DetailsViewState.init(), detailsReducer())
            .distinctUntilChanged()
            .observeOn(schedulers.mainThread())
            .compose(bindTestSubject())
            .subscribe { viewState?.renderView(it) }
    }

    private fun detailsReducer(): (DetailsViewState, DetailsPartialViewState) -> DetailsViewState {
        return { state, changes ->
            when (changes) {
                is DetailsPartialViewState.Result -> {
                    state.copy(resource = changes.resource)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getPostDetails(postId: Int) {
        onAction(Action.OpenPostDetails(postId))
    }

}