package com.babylone.playbook.data.models.user

data class UserResponseAddress(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: UserResponseGeo
)