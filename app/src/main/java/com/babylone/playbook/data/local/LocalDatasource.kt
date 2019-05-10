package com.babylone.playbook.data.local

import com.babylone.playbook.data.models.comment.CommentEntity
import com.babylone.playbook.data.models.post.PostEntity
import com.babylone.playbook.data.models.user.UserEntity
import com.babylone.playbook.domain.comment.Comment
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User
import io.reactivex.Flowable
import io.reactivex.Single

interface LocalDatasource {

    fun getUsers(): Flowable<List<User>>

    fun getPosts(): Flowable<List<Post>>

    fun getUser(userId: Int): Flowable<User>

    fun getPost(userId: Int): Flowable<Post>

    fun getComments(postId: Int): Flowable<List<Comment>>

    fun updateCache(userEntity: List<UserEntity>, postEntity: List<PostEntity>, commentsEntity: List<CommentEntity>)

    fun updateUsers(usersEntity: List<UserEntity>): Single<Unit>

    fun updatePosts(postEntity: List<PostEntity>): Single<Unit>

    fun updateComments(commentsEntity: List<CommentEntity>): Single<Unit>

}