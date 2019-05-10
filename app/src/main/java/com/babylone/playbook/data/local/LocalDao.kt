package com.babylone.playbook.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.babylone.playbook.data.models.comment.CommentEntity
import com.babylone.playbook.data.models.post.PostEntity
import com.babylone.playbook.data.models.user.UserEntity
import io.reactivex.Flowable

@Dao
interface LocalDao {

    @Query("SELECT * from user")
    fun getUsers(): Flowable<List<UserEntity>>

    @Query("SELECT * from user WHERE id = :userId ")
    fun getUserById(userId: Int): Flowable<UserEntity>

    @Query("SELECT * from post")
    fun getPosts(): Flowable<List<PostEntity>>

    @Query("SELECT * from post WHERE id = :postId ")
    fun getPostById(postId: Int): Flowable<PostEntity>

    @Query("SELECT * from comment")
    fun getComments(): List<CommentEntity>

    @Query("SELECT * from comment WHERE postId = :postId")
    fun getCommentsById(postId: Int): Flowable<List<CommentEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(post: PostEntity)

    @Insert(onConflict = REPLACE)
    fun insertPosts(posts: List<PostEntity>)

    @Insert(onConflict = REPLACE)
    fun insert(user: UserEntity)

    @Insert(onConflict = REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = REPLACE)
    fun insert(comment: CommentEntity)

    @Insert(onConflict = REPLACE)
    fun insertComments(comments: List<CommentEntity>)

    @Transaction
    fun updateLocalStorage(users: List<UserEntity>, posts: List<PostEntity>, comments: List<CommentEntity>) {
        insertUsers(users)
        insertPosts(posts)
        insertComments(comments)
    }

}