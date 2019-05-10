package com.babylone.playbook.domain.user

import io.reactivex.Flowable
import io.reactivex.Single

interface UserRepository {

    fun fetchUsers(): Single<List<User>>

    fun getUser(userId: Int): Flowable<User>

    fun save(userEntity: List<User>): Single<Unit>

}