package com.example.workoutcompanion.core.data.di

import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.NetworkObserverImpl
import com.example.workoutcompanion.core.data.user_database.module.UserProductionModule
import com.example.workoutcompanion.core.data.user_database.module.UserTestingModule
import com.example.workoutcompanion.core.data.workout.module.WorkoutModule
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
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

    @Provides
    @Singleton
  @Testing
    fun provideTestingAppStateManager(application : android.app.Application) : AppStateManager {
        return AppStateManager(
            workoutRepository = WorkoutModule.provideWorkoutRepository(
                application
            ) , profileRepository = UserTestingModule.provideProfileRepository()
        )
    }

    @Provides
    @Singleton
    @Production
    fun provideProductionAppStateManager(application : android.app.Application) : AppStateManager {
        return AppStateManager(
            workoutRepository = WorkoutModule.provideWorkoutRepository(
                application
            ) , profileRepository = UserProductionModule.provideProfileRepository(application)
        )
    }

}