package com.sopt.noostak.di

import com.sopt.data.repositoryimpl.ExampleRepositoryImpl
import com.sopt.data.repositoryimpl.UserInfoRepositoryImpl
import com.sopt.domain.repository.ExampleRepository
import com.sopt.domain.repository.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExampleRepository(exampleRepositoryImpl: ExampleRepositoryImpl): ExampleRepository

    @Binds
    @Singleton
    abstract fun bindUserInfoRepository(userInfoRepositoryImpl: UserInfoRepositoryImpl): UserInfoRepository

}
