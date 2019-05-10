package com.babylone.playbook.data

import android.content.Context
import com.babylone.playbook.BuildConfig
import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.data.local.ApplicationDataBase
import com.babylone.playbook.data.local.DefaultLocalDatasource
import com.babylone.playbook.data.local.LocalDatasource
import com.babylone.playbook.data.models.comment.CommentDataRepository
import com.babylone.playbook.data.models.post.PostsDataRepository
import com.babylone.playbook.data.models.user.UserDataRepository
import com.babylone.playbook.data.network.*
import com.babylone.playbook.domain.comment.CommentRepository
import com.babylone.playbook.domain.post.PostsRepository
import com.babylone.playbook.domain.user.UserRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providePostApi(okHttpClient: OkHttpClient): PostsApi {
        return RetrofitApiFactory.create(
            PostsApi::class.java,
            BuildConfig.BASE_API_URL,
            okHttpClient
        )
    }

    @Provides
    @Singleton
    fun providePostsRepository(
        networkDatasource: RemoteDatasource,
        localDatasource: LocalDatasource
    ): PostsRepository {
        return PostsDataRepository(networkDatasource, localDatasource)
    }

    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient): UsersApi {
        return RetrofitApiFactory.create(
            UsersApi::class.java,
            BuildConfig.BASE_API_URL,
            okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideCommentApi(okHttpClient: OkHttpClient): CommentsApi {
        return RetrofitApiFactory.create(
            CommentsApi::class.java,
            BuildConfig.BASE_API_URL,
            okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        networkDatasource: RemoteDatasource,
        localDatasource: LocalDatasource,
        schedulers: SchedulerProvider
    ): UserRepository {
        return UserDataRepository(networkDatasource, localDatasource, schedulers)
    }

    @Provides
    @Singleton
    fun provideCommentsRepository(
        networkDatasource: RemoteDatasource,
        localDatasource: LocalDatasource
    ): CommentRepository {
        return CommentDataRepository(networkDatasource, localDatasource)
    }

    @Provides
    @Singleton
    fun provideLocalDatasource(
        applicationDataBase: ApplicationDataBase,
        schedulers: SchedulerProvider
    ): LocalDatasource {
        return DefaultLocalDatasource(applicationDataBase, schedulers)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ApplicationDataBase {
        return ApplicationDataBase.newInstance(context)
    }

}