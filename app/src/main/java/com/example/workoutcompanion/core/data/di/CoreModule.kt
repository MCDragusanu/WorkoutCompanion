package com.example.workoutcompanion.core.data.di

import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.NetworkObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideNetworkObserver(application : android.app.Application) : NetworkObserver =
        NetworkObserverImpl(application.applicationContext)

}