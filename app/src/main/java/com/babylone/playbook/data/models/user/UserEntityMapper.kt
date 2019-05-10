package com.babylone.playbook.data.models.user

import com.babylone.playbook.data.models.comment.CommentEntity
import com.babylone.playbook.data.models.post.PostEntity
import com.babylone.playbook.domain.comment.Comment
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User

object UserEntityMapper {

    fun usersToDatabase(users: List<User>): List<UserEntity> {
        return users.map { UserEntity(it.id, it.name, it.username) }
    }

    fun fromDatabase(items: List<UserEntity>): List<User> {
        return items.map { User(it.id, it.name, it.username) }
    }

    fun fromDatabase(enitity: UserEntity): User {
        return User(enitity.id, enitity.name, enitity.username)
    }

    fun postsToDatabase(posts: Map<Int, List<Post>>): List<PostEntity> {
        val results = mutableListOf<PostEntity>()
        posts.map {
            val userId = it.key
            val items = it.value
            results += items.map { post -> PostEntity(userId, post.id, post.title, post.body) }
        }
        return results
    }

    fun commentsToDatabase(comments: Map<Int, List<Comment>>): List<CommentEntity> {
        val results = mutableListOf<CommentEntity>()
        comments.map {
            val postId = it.key
            val items = it.value
            results += items.map { comment ->
                CommentEntity(
                    postId,
                    comment.id,
                    comment.name,
                    comment.email,
                    comment.body
                )
            }
        }
        return results
    }

}
