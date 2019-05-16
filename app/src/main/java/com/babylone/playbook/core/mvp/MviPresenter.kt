package com.babylone.playbook.core.mvp

import io.reactivex.*
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.CancellationException

abstract class MviPresenter<Action, Result, State, View : MviView> {
    @Volatile
    protected var viewState: View? = null

    private var disposeEvent = PublishSubject.create<Boolean>()
    private val actionSubject = PublishSubject.create<Action>()
    private val stateConsumer = ReplaySubject.create<State>()

    protected fun <T> bindFlowableLifecycleTransformer(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.takeUntil(disposeEvent.toFlowable(BackpressureStrategy.DROP))
        }
    }

    protected fun <T> bindSingleLifecycleTransformer(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.takeUntil(disposeEvent.firstOrError())
        }
    }

    protected fun <T> bindObservableLifecycleTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.takeUntil(disposeEvent)
        }
    }

    protected fun bindCompletableLifecycleTransformer(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.takeUntil(disposeEvent.flatMapCompletable {
                Completable.error(CancellationException())
            })
        }
    }

    fun attachView(view: View) {
        disposeEvent = PublishSubject.create<Boolean>()
        viewState?.let {
            throw RuntimeException("Previous View should be unbinded :$view")
        } ?: run {
            viewState = view
            onFirstViewAttach()
        }
    }

    fun detachView(view: View) {
        if (viewState != view) {
            throw RuntimeException("View to bind and current View not equals :$view")
        } else {
            viewState = null
        }
        disposeEvent.onNext(true)
        disposeEvent.onComplete()
    }

    protected fun observeAction() = actionSubject.toSerialized()

    fun onAction(action: Action) {
        actionSubject.onNext(action)
    }

    fun bindStateSubject(): ObservableTransformer<State, State> {
        return ObservableTransformer { actions ->
            actions.doOnNext { stateConsumer.onNext(it) }
        }
    }

    fun stateSubject() = stateConsumer

    open fun onFirstViewAttach() {}

}