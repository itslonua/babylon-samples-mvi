package com.babylone.playbook.data.models.comment

import com.babylone.playbook.data.local.LocalDatasource
import com.babylone.playbook.data.models.user.UserEntityMapper
import com.babylone.playbook.data.network.RemoteDatasource
import com.babylone.playbook.domain.comment.Comment
import com.babylone.playbook.domain.comment.CommentRepository
import com.babylone.playbook.domain.post.Post
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

internal class CommentDataRepository(
    private val networkDatasource: RemoteDatasource,
    private val localDatasource: LocalDatasource
) : CommentRepository {

    private val cacheSubject = PublishSubject.create<List<Comment>>()

    init {
        cacheSubject
                .map { it.groupBy { it.postId } }
                .switchMapSingle { save(it) }.subscribe()
    }

    override fun getCommentByPostId(postId: Int): Flowable<List<Comment>> {
        return localDatasource.getComments(postId)
            .onErrorReturn { emptyList() }
    }

    override fun fetchComments(): Single<List<Comment>> {
        return networkDatasource.getComments()
                .doOnSuccess { cacheSubject.onNext(it) }
            .onErrorReturn { emptyList() }
    }

    override fun save(comments: Map<Int, List<Comment>>): Single<Unit> {
        return Single.just(comments)
            .map(UserEntityMapper::commentsToDatabase)
            .flatMap(localDatasource::updateComments)
    }
}
