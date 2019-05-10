package com.babylone.playbook.data.models.user

import com.babylone.playbook.domain.user.User

object UserResponseMapper {

    fun fromNetwork(users: List<UserResponse>): List<User> {
        return users.map {
            User(
                it.id,
                it.name,
                it.username
            )
        }
    }
}
