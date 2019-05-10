package com.babylone.playbook.data.network

import com.babylone.playbook.domain.comment.Comment
import com.babylone.playbook.domain.post.Post
import com.babylone.playbook.domain.user.User
import io.reactivex.Single

interface RemoteDatasource {

    fun getUsers(): Single<List<User>>

    fun getPosts(): Single<List<Post>>

    fun getComments(): Single<List<Comment>>

}