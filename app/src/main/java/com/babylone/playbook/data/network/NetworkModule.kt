package com.babylone.playbook.data.network

import com.babylone.playbook.core.mvp.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(factory: NetworkTransportInterceptorsFactory) =
        NetworkTransportFactory.createOkHttpClient(factory)

    @Provides
    fun networkIntercepterFactory() = NetworkTransportInterceptorsFactory()

    @Provides
    fun provideNetworkDatasource(
        userApi: UsersApi,
        postsApi: PostsApi,
        commentsApi: CommentsApi,
        schedulers: SchedulerProvider
    ): RemoteDatasource {
        return DefaultNetworkDatasource(userApi, postsApi, commentsApi, schedulers)
    }
}