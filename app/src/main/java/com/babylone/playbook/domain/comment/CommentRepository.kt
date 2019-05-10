package com.babylone.playbook.domain.comment

import io.reactivex.Flowable
import io.reactivex.Single

interface CommentRepository {

    fun fetchComments(): Single<List<Comment>>

    fun getCommentByPostId(postId: Int): Flowable<List<Comment>>

    fun save(comments: Map<Int, List<Comment>>): Single<Unit>

}