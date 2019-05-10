package com.babylone.playbook.data.models.post

import com.babylone.playbook.domain.post.Post

object PostEntityMapper {

    fun fromDatabase(postEntity: PostEntity): Post {
        return Post(postEntity.userId, postEntity.id, postEntity.title, postEntity.body)
    }

    fun fromDatabase(items: List<PostEntity>): List<Post> {
        return items.map(this::fromDatabase)
    }

}