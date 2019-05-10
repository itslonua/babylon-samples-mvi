package com.babylone.playbook.domain.post

import io.reactivex.Flowable
import io.reactivex.Single

interface PostsRepository {

    fun fetchPosts(): Single<List<Post>>

    fun getPosts(): Flowable<List<Post>>

    fun getPost(postId: Int): Flowable<Post>

    fun save(posts: Map<Int, List<Post>>): Single<Unit>

}