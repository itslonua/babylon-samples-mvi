package com.babylone.playbook.domain.fetcher

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.comment.CommentRepository
import com.babylone.playbook.domain.post.PostsRepository
import com.babylone.playbook.domain.user.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.Singles

class FetcherUseCase(
    private val postsRepository: PostsRepository,
    private val userRepository: UserRepository,
    private val commentsRepository: CommentRepository,
    private val schedulers: SchedulerProvider
) {

    fun prefetch(): Single<Unit> {
        return Singles.zip(
            userRepository.fetchUsers(),
            postsRepository.fetchPosts(),
            commentsRepository.fetchComments()
        ) { _, _, _ -> Unit }
            .subscribeOn(schedulers.io())
    }

    fun fetched(): Single<Boolean> {
        return postsRepository.fetchPosts().map { it.isNotEmpty() }
    }

}