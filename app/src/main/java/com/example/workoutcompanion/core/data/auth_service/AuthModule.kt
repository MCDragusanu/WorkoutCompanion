package com.example.workoutcompanion.core.data.auth_service

import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.NetworkObserverImpl
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.di.Testing
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
   @Production
    fun provideProductionAuthService() : AuthManager = AuthManagerImpl()

    @Provides
    @Singleton
    @Testing()
    fun provideTestAuthService(): AuthManager = AuthManagerTestImpl()

}