package com.babylone.playbook.domain.user

data class User(
    val id: Int,
    val name: String,
    val username: String
) {
    companion object {

        fun empty() = User(0, "", "")

    }
}