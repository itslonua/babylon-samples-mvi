package com.babylone.playbook.data.network

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.data.models.comment.CommentResponseMapper
import com.babylone.playbook.data.models.post.PostResponseMapper
import com.babylone.playbook.data.models.user.UserResponseMapper
import com.babylone.playbook.domain.comment.Comment
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User
import io.reactivex.Single

class DefaultNetworkDatasource(
    private val usersApi: UsersApi,
    private val postsApi: PostsApi,
    private val commentsApi: CommentsApi,
    private val schedulers: SchedulerProvider
) : RemoteDatasource {

    override fun getUsers(): Single<List<User>> {
        return usersApi.getUsers()
            .map(UserResponseMapper::fromNetwork)
            .onErrorReturn { emptyList() }
            .subscribeOn(schedulers.io())
    }

    override fun getPosts(): Single<List<Post>> {
        return postsApi.getPosts()
            .map(PostResponseMapper::fromNetwork)
            .onErrorReturn { emptyList() }
            .subscribeOn(schedulers.io())
    }

    override fun getComments(): Single<List<Comment>> {
        return commentsApi.getComments()
            .map(CommentResponseMapper::fromNetwork)
            .onErrorReturn { emptyList() }
            .subscribeOn(schedulers.io())
    }

}