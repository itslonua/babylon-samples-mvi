package com.babylone.playbook.data.models.post

import com.babylone.playbook.domain.post.Post

object PostResponseMapper {

    fun fromNetwork(posts: List<PostResponse>): List<Post> {
        return posts.map { Post(it.userId, it.id, it.title, it.body) }
    }

}