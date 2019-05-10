package com.babylone.playbook.data.models.post

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Post response model
 */
@Entity(tableName = "post")
data class PostEntity(
    val userId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val body: String
)
