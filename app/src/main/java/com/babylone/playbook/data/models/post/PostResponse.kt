package com.babylone.playbook.data.models.post

/**
 * Post response model
 */
data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
