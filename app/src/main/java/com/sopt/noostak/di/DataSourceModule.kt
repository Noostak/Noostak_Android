package com.sopt.noostak.di

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.datasource.ExampleDataSource
import com.sopt.data.datasourceimpl.AppointmentConfirmDataSourceImpl
import com.sopt.data.datasourceimpl.ExampleDataSourceImpl
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
    abstract fun bindAppointmentConfirmDataSource(appointmentConfirmDataSourceImpl: AppointmentConfirmDataSourceImpl): AppointmentConfirmDataSource
}
