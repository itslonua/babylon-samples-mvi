package com.babylone.playbook.domain

import com.babylone.playbook.core.mvp.SchedulerProvider
import com.babylone.playbook.domain.comment.CommentRepository
import com.babylone.playbook.domain.details.DetailsUseCase
import com.babylone.playbook.domain.fetcher.FetcherUseCase
import com.babylone.playbook.domain.post.PostUseCase
import com.babylone.playbook.domain.post.PostsRepository
import com.babylone.playbook.domain.user.UserRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideLoaderUseCase(
        postsRepository: PostsRepository,
        userRepository: UserRepository,
        commentsRepository: CommentRepository,
        schedulers: SchedulerProvider
    ) = FetcherUseCase(
        postsRepository,
        userRepository,
        commentsRepository,
        schedulers
    )

    @Provides
    fun provideDetailsUseCase(
        postsRepository: PostsRepository,
        userRepository: UserRepository,
        commentsRepository: CommentRepository,
        schedulers: SchedulerProvider
    ) = DetailsUseCase(
        postsRepository,
        userRepository,
        commentsRepository,
        schedulers
    )

    @Provides
    fun provideMainUseCase(
        repository: PostsRepository,
        schedulers: SchedulerProvider
    ) = PostUseCase(repository, schedulers)

}