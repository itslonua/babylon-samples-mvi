package com.babylone.playbook.ui.main

import com.babylone.playbook.core.Resource
import com.babylone.playbook.core.mvp.TestSchedulersFactory
import com.babylone.playbook.domain.post.PostUseCase
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import java.lang.RuntimeException
import org.mockito.Mockito.doNothing


class MainActivityPresenterTest {

    private val mockView = mock<MainActivityView>()
    private val mainUseCase = mock<PostUseCase>()
    private val mockNavigator = mock<MainNavigator>()
    private val sut = MainActivityPresenter(mockNavigator, mainUseCase, TestSchedulersFactory)

    @Test
    fun `reloadScreen should produce loading and success states`() {
        whenever(mainUseCase.getTitles()).thenReturn(Flowable.just(emptyList()))

        sut.attachView(mockView)
        sut.reloadScreen()

        val states = listOf(
            MainActivityViewState(Resource.loading()),
            MainActivityViewState(Resource.success(emptyList()))
        )
        states.forEach { verify(mockView, atLeastOnce()).renderView(it) }
    }

    @Test
    fun `reloadScreen should produce loading and error states`() {
        val exception = RuntimeException()
        whenever(mainUseCase.getTitles()).thenReturn(Flowable.error(exception))

        sut.attachView(mockView)
        sut.reloadScreen()

        val states = listOf(
            MainActivityViewState(Resource.loading()),
            MainActivityViewState(Resource.error(exception))
        )
        states.forEach { verify(mockView, atLeastOnce()).renderView(it) }
    }

    @Test
    fun `onPostClicked should invoke showPostDetails on MainNavigator with postId equals 10`() {
        val valueCapture = ArgumentCaptor.forClass(Int::class.java)
        doNothing().whenever(mockNavigator).showPostDetails(valueCapture.capture())

        sut.onPostClicked(10)

        assertEquals(10, valueCapture.value)
    }

}