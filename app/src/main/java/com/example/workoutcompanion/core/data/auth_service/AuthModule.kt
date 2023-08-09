package com.example.workoutcompanion.core.data.auth_service

import com.example.workoutcompanion.core.data.di.ComponentType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
   @ComponentType(testing = false)
    fun provideProductionAuthService() : AuthManager = AuthManagerImpl()

    @Provides
    @Singleton
    @ComponentType(testing = true)
    fun provideTestAuthService(): AuthManager = AuthManagerTestImpl()

}