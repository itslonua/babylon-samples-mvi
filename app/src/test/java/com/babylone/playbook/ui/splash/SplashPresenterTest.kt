package com.babylone.playbook.ui.splash

import com.babylone.playbook.core.Resource
import com.babylone.playbook.core.mvp.TestSchedulersFactory
import com.babylone.playbook.domain.fetcher.FetcherUseCase
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Test

import java.lang.RuntimeException

class SplashPresenterTest {

    private val mockView = mock<SplashView>()
    private val mockFetcherUseCase = mock<FetcherUseCase>()
    private val presenter = SplashPresenter(mockFetcherUseCase, TestSchedulersFactory)

    @Test
    fun `initialize should produce loading and success states`() {
        whenever(mockFetcherUseCase.prefetch()).thenReturn(Observable.just(Unit))

        presenter.attachView(mockView)
        presenter.initialize()

        val loadingState = SplashViewState(Resource.loading())
        val successState = SplashViewState(Resource.success(true))
        verify(mockView, atLeastOnce()).renderView(loadingState)
        verify(mockView, atLeastOnce()).renderView(successState)
        val t = presenter.testConsumer.test()
        t.assertValues(loadingState, successState)
    }

    @Test
    fun `initialize should produce loading and error states`() {
        val exception = RuntimeException()
        whenever(mockFetcherUseCase.prefetch()).thenReturn(Observable.error(exception))

        presenter.attachView(mockView)
        presenter.initialize()

        val loadingState = SplashViewState(Resource.loading())
        val errorState = SplashViewState(Resource.error(exception))
        verify(mockView, atLeastOnce()).renderView(loadingState)
        verify(mockView, atLeastOnce()).renderView(errorState)
        val t = presenter.testConsumer.test()
        t.assertValues(loadingState, errorState)
    }

}