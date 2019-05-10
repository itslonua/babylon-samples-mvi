package com.babylone.playbook.domain.post

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.post.PostsRepository
import io.reactivex.Flowable

class PostUseCase(
    private val repository: PostsRepository,
    private val schedulers: SchedulerProvider
) {

    fun getTitles(): Flowable<List<Post>> {
        return repository.getPosts()
            .map { posts -> posts.map { post -> post.asTitle() } }
            .observeOn(schedulers.io())
    }

}