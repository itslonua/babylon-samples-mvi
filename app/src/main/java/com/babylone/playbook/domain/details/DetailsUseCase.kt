package com.babylone.playbook.domain.details

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.comment.CommentRepository
import com.babylone.playbook.domain.post.PostsRepository
import com.babylone.playbook.domain.user.UserRepository
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables

class DetailsUseCase(
    private val postsRepository: PostsRepository,
    private val userRepository: UserRepository,
    private val commentsRepository: CommentRepository,
    private val scheduler: SchedulerProvider
) {

    fun getPostDetails(postId: Int): Flowable<DetailsModel> {
        val postFlowable = postsRepository.getPost(postId)
            .share()
            .cacheWithInitialCapacity(1)
            .subscribeOn(scheduler.io())

        val userFlowable = postFlowable.map { it.userId }
            .switchMap(userRepository::getUser)
            .subscribeOn(scheduler.io())

        val commentsCountFlowable = postFlowable.map { it.id }
            .switchMap(commentsRepository::getCommentByPostId)
            .map { it.size }
            .subscribeOn(scheduler.io())

        return Flowables.zip(postFlowable, userFlowable, commentsCountFlowable, ::DetailsModel)
    }
}