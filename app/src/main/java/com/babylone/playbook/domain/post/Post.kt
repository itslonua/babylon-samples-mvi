package com.babylone.playbook.domain.post

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    companion object {

        fun empty() = Post(-1, -1, "", "")

    }

    fun asTitle() = Post(-1, id, title, "")
}