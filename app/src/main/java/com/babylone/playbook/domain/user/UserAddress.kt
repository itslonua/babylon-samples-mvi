package com.babylone.playbook.domain.user

data class UserAddress(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: UserGeo
)