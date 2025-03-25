package com.sopt.noostak.di

import com.sopt.data.service.AccountApiService
import com.sopt.data.service.AppointmentConfirmApiService
import com.sopt.data.service.AppointmentCreateApiService
import com.sopt.data.service.AuthApiService
import com.sopt.data.service.CalendarApiService
import com.sopt.data.service.ExampleApiService
import com.sopt.data.service.GroupApiService
import com.sopt.data.service.GroupDetailApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideExampleService(
        @AccessToken retrofit: Retrofit
    ): ExampleApiService = retrofit.create(ExampleApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(
        @WithoutTokenInterceptor retrofit: Retrofit
    ): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideAccountApiService(
        @AccessToken retrofit: Retrofit
    ): AccountApiService = retrofit.create(AccountApiService::class.java)

    @Provides
    @Singleton
    fun provideAppointmentConfirmService(
        @AccessToken retrofit: Retrofit
    ): AppointmentConfirmApiService = retrofit.create(AppointmentConfirmApiService::class.java)

    @Provides
    @Singleton
    fun provideCalendarService(
        @AccessToken retrofit: Retrofit
    ): CalendarApiService = retrofit.create(CalendarApiService::class.java)

    @Provides
    @Singleton
    fun provideGroupApiService(@AccessToken retrofit: Retrofit): GroupApiService =
        retrofit.create(GroupApiService::class.java)

    @Provides
    @Singleton
    fun provideAppointmentCreateService(
        @AccessToken retrofit: Retrofit
    ): AppointmentCreateApiService = retrofit.create(AppointmentCreateApiService::class.java)

    @Provides
    @Singleton
    fun provideGroupDetailApiService(
        @AccessToken retrofit: Retrofit
    ): GroupDetailApiService = retrofit.create(GroupDetailApiService::class.java)
}
