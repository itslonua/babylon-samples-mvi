package com.babylone.playbook.data.local

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.data.models.comment.CommentEntity
import com.babylone.playbook.data.models.comment.CommentEntityMapper
import com.babylone.playbook.data.models.post.PostEntity
import com.babylone.playbook.data.models.post.PostEntityMapper
import com.babylone.playbook.data.models.user.UserEntity
import com.babylone.playbook.data.models.user.UserEntityMapper
import com.babylone.playbook.domain.comment.Comment
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User
import io.reactivex.Flowable
import io.reactivex.Single

class DefaultLocalDatasource(
    private val applicationDataBase: ApplicationDataBase,
    private val schedulers: SchedulerProvider
) : LocalDatasource {

    override fun getUsers(): Flowable<List<User>> {
        return applicationDataBase.dao()
            .getUsers()
            .map(UserEntityMapper::fromDatabase)
            .observeOn(schedulers.io())
    }

    override fun getPosts(): Flowable<List<Post>> {
        return applicationDataBase.dao()
            .getPosts()
            .map(PostEntityMapper::fromDatabase)
            .observeOn(schedulers.io())
    }

    override fun getUser(userId: Int): Flowable<User> {
        return applicationDataBase.dao()
            .getUserById(userId)
            .map(UserEntityMapper::fromDatabase)
            .observeOn(schedulers.io())
    }

    override fun getPost(userId: Int): Flowable<Post> {
        return applicationDataBase.dao()
            .getPostById(userId)
            .map(PostEntityMapper::fromDatabase)
            .observeOn(schedulers.io())
    }

    override fun getComments(postId: Int): Flowable<List<Comment>> {
        return applicationDataBase.dao()
            .getCommentsById(postId)
            .map(CommentEntityMapper::fromDatabase)
            .observeOn(schedulers.io())
    }

    override fun updateCache(
        userEntity: List<UserEntity>,
        postEntity: List<PostEntity>,
        commentsEntity: List<CommentEntity>
    ) {
        applicationDataBase.dao().updateLocalStorage(userEntity, postEntity, commentsEntity)
    }

    override fun updateUsers(usersEntity: List<UserEntity>): Single<Unit> {
        return Single.fromCallable {
            applicationDataBase.dao().insertUsers(usersEntity)
        }.observeOn(schedulers.io())
    }

    override fun updatePosts(postEntity: List<PostEntity>): Single<Unit> {
        return Single.fromCallable {
            applicationDataBase.dao().insertPosts(postEntity)
        }.observeOn(schedulers.io())
    }

    override fun updateComments(commentsEntity: List<CommentEntity>): Single<Unit> {
        return Single.fromCallable {
            applicationDataBase.dao().insertComments(commentsEntity)
        }.observeOn(schedulers.io())
    }

}