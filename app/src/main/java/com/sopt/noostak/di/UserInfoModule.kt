package com.sopt.noostak.di

import com.sopt.data.datasource.UserDataSource
import com.sopt.data.datasourceimpl.UserDataSourceImpl
import com.sopt.data.repositoryimpl.UserInfoRepositoryImpl
import com.sopt.domain.repository.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserInfoModule {
    @Binds
    @Singleton
    abstract fun bindsAuthDataSource(
        authDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authDataSourceImpl: UserInfoRepositoryImpl
    ): UserInfoRepository
}
