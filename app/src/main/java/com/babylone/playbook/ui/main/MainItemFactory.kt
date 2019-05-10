package com.babylone.playbook.ui.main

import com.babylone.playbook.domain.post.Post

internal object MainItemFactory {

    fun fromPost(post: Post): MainItem {

        return MainItem(post.id, post.title)

    }

}