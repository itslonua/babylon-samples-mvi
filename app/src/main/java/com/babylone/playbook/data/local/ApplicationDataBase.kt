package com.babylone.playbook.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.babylone.playbook.data.models.comment.CommentEntity
import com.babylone.playbook.data.models.post.PostEntity
import com.babylone.playbook.data.models.user.UserEntity

@Database(
    entities = [CommentEntity::class, PostEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDataBase : RoomDatabase() {

    companion object {

        private const val DATABASE = "database.db"

        fun newInstance(context: Context): ApplicationDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ApplicationDataBase::class.java,
                DATABASE
            )
                .build()
        }
    }

    abstract fun dao(): LocalDao
}