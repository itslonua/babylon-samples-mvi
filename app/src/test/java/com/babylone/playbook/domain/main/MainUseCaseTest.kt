package com.babylone.playbook.domain.main

import com.babylone.playbook.core.mvp.TestSchedulersFactory
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.post.PostUseCase
import com.babylone.playbook.domain.post.PostsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Test

class MainUseCaseTest {

    private val repository = mock<PostsRepository>()
    private val schedulers = TestSchedulersFactory

    private val sut = PostUseCase(repository, schedulers)

    @Test
    fun getTitles() {
        val result = emptyList<Post>()
        whenever(repository.getPosts()).thenReturn(Flowable.just(result))

        sut.getTitles()
            .test()
            .assertResult(result)
    }
}