package com.example.workoutcompanion.entry_navigation.login.module

import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.NetworkObserverImpl
import com.example.workoutcompanion.entry_navigation.login.auth_service.AuthManager
import com.example.workoutcompanion.entry_navigation.login.auth_service.AuthManagerImpl
import com.example.workoutcompanion.entry_navigation.login.auth_service.AuthManagerTestImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    const val testImpl = "authTestImpl"
    const val prodImpl = "authProdImpl"
    @Provides
    @Singleton
    @Named("authProdImpl")
    fun provideProductionAuthService() : AuthManager = AuthManagerImpl()

    @Provides
    @Singleton
    @Named("authTestImpl")
    fun provideTestAuthService(): AuthManager = AuthManagerTestImpl()
    @Provides
    @Singleton
    fun provideNetworkObserver(application: android.app.Application):NetworkObserver = NetworkObserverImpl(application.applicationContext)




}