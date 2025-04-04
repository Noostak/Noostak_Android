package com.sopt.noostak.di

import com.sopt.data.datasource.AccountDataSource
import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.datasource.AppointmentCreateDataSource
import com.sopt.data.datasource.AuthDataSource
import com.sopt.data.datasource.CalendarDataSource
import com.sopt.data.datasource.ExampleDataSource
import com.sopt.data.datasource.GroupDataSource
import com.sopt.data.datasource.GroupDetailDataSource
import com.sopt.data.datasource.ProfileDataSource
import com.sopt.data.datasource.UserDataSource
import com.sopt.data.datasourceimpl.AccountDataSourceImpl
import com.sopt.data.datasourceimpl.AppointmentConfirmDataSourceImpl
import com.sopt.data.datasourceimpl.AppointmentCreateDataSourceImpl
import com.sopt.data.datasourceimpl.AuthDataSourceImpl
import com.sopt.data.datasourceimpl.CalendarDataSourceImpl
import com.sopt.data.datasourceimpl.ExampleDataSourceImpl
import com.sopt.data.datasourceimpl.GroupDataSourceImpl
import com.sopt.data.datasourceimpl.GroupDetailDataSourceImpl
import com.sopt.data.datasourceimpl.ProfileDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindCalendarDataSource(calendarDataSourceImpl: CalendarDataSourceImpl): CalendarDataSource

    @Binds
    @Singleton
    abstract fun bindGroupDataSource(groupDataSourceImpl: GroupDataSourceImpl): GroupDataSource

    @Binds
    @Singleton
    abstract fun bindAppointmentCreateDataSource(appointmentCreateDataSourceImpl: AppointmentCreateDataSourceImpl): AppointmentCreateDataSource

    @Binds
    @Singleton
    abstract fun bindGroupDetailDataSource(groupDetailDataSourceImpl: GroupDetailDataSourceImpl): GroupDetailDataSource

    @Binds
    @Singleton
    abstract fun bindProfileDataSource(profileDataSourceImpl: ProfileDataSourceImpl): ProfileDataSource
}
