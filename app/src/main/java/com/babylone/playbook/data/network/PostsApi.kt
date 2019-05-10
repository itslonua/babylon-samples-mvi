package com.babylone.playbook.data.network

import com.babylone.playbook.data.models.post.PostResponse
import io.reactivex.Single
import retrofit2.http.GET

interface PostsApi {

    @GET("/posts")
    fun getPosts(): Single<List<PostResponse>>

}