package com.babylone.playbook.domain.fetcher

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.comment.CommentRepository
import com.babylone.playbook.domain.post.PostsRepository
import com.babylone.playbook.domain.user.UserRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class FetcherUseCase(
    private val postsRepository: PostsRepository,
    private val userRepository: UserRepository,
    private val commentsRepository: CommentRepository,
    private val schedulers: SchedulerProvider
) {

    fun prefetch(): Observable<Unit> {
        return Observables.zip(
            userRepository.fetchUsers()
                .flatMap { userRepository.save(it) }.toObservable(),
            postsRepository.fetchPosts().map { it -> it.groupBy { it.userId } }
                .flatMap { postsRepository.save(it) }.toObservable(),
            commentsRepository.fetchComments().map { it -> it.groupBy { it.postId } }
                .flatMap { commentsRepository.save(it) }.toObservable()
        ) { _, _, _ -> Unit }
            .subscribeOn(schedulers.io())
    }

}