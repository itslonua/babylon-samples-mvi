package com.babylone.playbook.data.models.post

import com.babylone.playbook.data.local.LocalDatasource
import com.babylone.playbook.data.models.user.UserEntityMapper
import com.babylone.playbook.data.network.RemoteDatasource
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.post.PostsRepository
import com.babylone.playbook.domain.user.User
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

internal class PostsDataRepository(
    private val remoteDatasource: RemoteDatasource,
    private val localDatasource: LocalDatasource
) : PostsRepository {

    private val cacheSubject = PublishSubject.create<List<Post>>()

    init {
        cacheSubject
                .map { it.groupBy { it.userId } }
                .switchMapSingle { save(it) }.subscribe()
    }

    override fun fetchPosts(): Single<List<Post>> {
        return remoteDatasource.getPosts()
                .doOnSuccess { cacheSubject.onNext(it) }
            .onErrorReturn { emptyList() }
    }

    override fun getPosts(): Flowable<List<Post>> {
        return localDatasource.getPosts()
            .onErrorReturn { emptyList() }
    }

    override fun getPost(postId: Int): Flowable<Post> {
        return localDatasource.getPost(postId)
    }

    override fun save(posts: Map<Int, List<Post>>): Single<Unit> {
        return Single.just(posts)
            .map(UserEntityMapper::postsToDatabase)
            .flatMap(localDatasource::updatePosts)
    }

}
