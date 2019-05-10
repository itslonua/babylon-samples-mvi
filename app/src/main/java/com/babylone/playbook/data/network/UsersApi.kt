package com.babylone.playbook.data.network

import com.babylone.playbook.data.models.user.UserResponse
import io.reactivex.Single
import retrofit2.http.GET

interface UsersApi {

    @GET("/users")
    fun getUsers(): Single<List<UserResponse>>

}