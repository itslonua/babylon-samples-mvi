package com.babylone.playbook.data.models.comment

import com.babylone.playbook.domain.comment.Comment

object CommentEntityMapper {

    fun fromDatabase(comment: CommentEntity): Comment {
        return Comment(comment.postId, comment.id, comment.name, comment.email, comment.body)
    }

    fun fromDatabase(comments: List<CommentEntity>): List<Comment> {
        return comments.map { Comment(it.postId, it.id, it.name, it.email, it.body) }
    }

}