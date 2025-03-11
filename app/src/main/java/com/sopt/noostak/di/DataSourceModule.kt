package com.sopt.noostak.di

import com.sopt.data.datasource.AccountDataSource
import com.sopt.data.datasource.AuthDataSource
import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.datasource.ExampleDataSource
import com.sopt.data.datasource.UserDataSource
import com.sopt.data.datasourceimpl.AccountDataSourceImpl
import com.sopt.data.datasourceimpl.AuthDataSourceImpl
import com.sopt.data.datasourceimpl.AppointmentConfirmDataSourceImpl
import com.sopt.data.datasourceimpl.ExampleDataSourceImpl
import com.sopt.data.datasourceimpl.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindExampleDataSource(exampleDataSourceImpl: ExampleDataSourceImpl): ExampleDataSource

    @Binds
    @Singleton
    abstract fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindAccountDataSource(accountDataSource: AccountDataSourceImpl): AccountDataSource

    @Binds
    @Singleton
    abstract fun bindAppointmentConfirmDataSource(appointmentConfirmDataSourceImpl: AppointmentConfirmDataSourceImpl): AppointmentConfirmDataSource
}
