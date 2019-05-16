package com.babylone.playbook.ui.details

import com.babylone.playbook.core.Resource
import com.babylone.playbook.core.mvp.TestSchedulersFactory
import com.babylone.playbook.domain.details.DetailsModel
import com.babylone.playbook.domain.details.DetailsUseCase
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Test

import java.lang.RuntimeException

class DetailsActivityPresenterTest {

    private val mockView = mock<DetailsView>()
    private val detailsUseCase = mock<DetailsUseCase>()
    private val sut = DetailsActivityPresenter(detailsUseCase, TestSchedulersFactory)

    @Test
    fun `initialize should produce loading and success states`() {
        val model = DetailsModel(Post.empty(), User.empty(), 0)
        whenever(detailsUseCase.getPostDetails(any())).thenReturn(Flowable.just(model))

        sut.attachView(mockView)
        sut.getPostDetails(any())

        val loadingState = DetailsViewState(Resource.loading())
        val result = DetailPostItemFactory.create(model)
        val successState = DetailsViewState(Resource.success(result))
        verify(mockView, atLeastOnce()).renderView(loadingState)
        verify(mockView, atLeastOnce()).renderView(successState)
        val t = sut.stateSubject().test()
        t.assertSubscribed()
            .assertValues(loadingState, successState)
            .assertNoErrors()
    }

    @Test
    fun `initialize should produce loading and error states`() {
        val exception = RuntimeException()
        whenever(detailsUseCase.getPostDetails(any())).thenReturn(Flowable.error(exception))

        sut.attachView(mockView)
        sut.getPostDetails(any())

        val loadingState = DetailsViewState(Resource.loading())
        val errorState = DetailsViewState(Resource.error(exception))
        verify(mockView, atLeastOnce()).renderView(loadingState)
        verify(mockView, atLeastOnce()).renderView(errorState)
        val t = sut.stateSubject().test()
        t.assertValues(loadingState, errorState)
    }
}