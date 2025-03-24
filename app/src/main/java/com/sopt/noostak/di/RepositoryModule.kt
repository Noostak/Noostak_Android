package com.sopt.noostak.di

import com.sopt.data.repositoryimpl.AccountRepositoryImpl
import com.sopt.data.repositoryimpl.AppointmentConfirmRepositoryImpl
import com.sopt.data.repositoryimpl.AuthRepositoryImpl
import com.sopt.data.repositoryimpl.ExampleRepositoryImpl
import com.sopt.data.repositoryimpl.GroupRepositoryImpl
import com.sopt.data.repositoryimpl.UserInfoRepositoryImpl
import com.sopt.domain.repository.AccountRepository
import com.sopt.domain.repository.AppointmentConfirmRepository
import com.sopt.domain.repository.AuthRepository
import com.sopt.domain.repository.ExampleRepository
import com.sopt.domain.repository.GroupRepository
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

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindAppointmentConfirmRepository(appointmentConfirmRepositoryImpl: AppointmentConfirmRepositoryImpl): AppointmentConfirmRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(groupRepositoryImpl: GroupRepositoryImpl): GroupRepository
}
