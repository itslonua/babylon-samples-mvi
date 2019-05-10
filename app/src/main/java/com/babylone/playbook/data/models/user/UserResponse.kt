package com.babylone.playbook.data.models.user

data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: UserResponseAddress,
    val phone: String,
    val website: String,
    val company: UserResponseCompany
)