package com.babylone.playbook.domain.details

import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User

data class DetailsModel(
    val post: Post,
    val user: User,
    val comments: Int
)