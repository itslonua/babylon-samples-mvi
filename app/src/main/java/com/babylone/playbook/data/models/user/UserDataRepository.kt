package com.babylone.playbook.data.models.user

import android.annotation.SuppressLint
import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.data.local.LocalDatasource
import com.babylone.playbook.data.network.RemoteDatasource
import com.babylone.playbook.domain.user.User
import com.babylone.playbook.domain.user.UserRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject


internal class UserDataRepository(
        private val networkDatasource: RemoteDatasource,
        private val localDatasource: LocalDatasource,
        private val schedulers: SchedulerProvider
) : UserRepository {

    private val cacheSubject = PublishSubject.create<List<User>>()

    init {
        cacheSubject.switchMapSingle { save(it) }.subscribe()
    }

    override fun fetchUsers(): Single<List<User>> {
        return networkDatasource.getUsers()
                .doOnSuccess { cacheSubject.onNext(it) }
                .onErrorReturn { emptyList() }
    }

    override fun getUser(userId: Int): Flowable<User> {
        return localDatasource.getUser(userId)
    }

    @SuppressLint("CheckResult")
    override fun save(userEntity: List<User>): Single<Unit> {
        return Single.just(userEntity)
                .map(UserEntityMapper::usersToDatabase)
                .flatMap(localDatasource::updateUsers)
                .subscribeOn(schedulers.io())
    }

}
