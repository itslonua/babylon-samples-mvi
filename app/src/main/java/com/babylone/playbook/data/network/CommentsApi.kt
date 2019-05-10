package com.babylone.playbook.data.network

import com.babylone.playbook.data.models.comment.CommentResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CommentsApi {

    @GET("/comments")
    fun getComments(): Single<List<CommentResponse>>

}