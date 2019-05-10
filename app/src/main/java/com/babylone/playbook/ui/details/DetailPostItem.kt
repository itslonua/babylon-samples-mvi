package com.babylone.playbook.ui.details

data class DetailPostItem(
    val title: String,
    val body: String,
    val author: String,
    val avatarUrl: String,
    val comments: Int = 0
)