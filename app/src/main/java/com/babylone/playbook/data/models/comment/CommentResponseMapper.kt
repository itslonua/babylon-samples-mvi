package com.babylone.playbook.data.models.comment

import com.babylone.playbook.domain.comment.Comment

object CommentResponseMapper {

    fun fromNetwork(comments: List<CommentResponse>): List<Comment> {
        return comments.map { Comment(it.postId, it.id, it.name, it.email, it.body) }
    }

}
