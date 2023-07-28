package com.example.workoutcompanion.core.data.workout_tracking.module

import android.app.Application
import com.example.workoutcompanion.core.data.workout_tracking.WorkoutRepositortImpl
import com.example.workoutcompanion.core.data.workout_tracking.WorkoutRepository
import com.example.workoutcompanion.core.data.workout_tracking.database.WorkoutDatabase
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkoutModule {
    @Provides
    fun provideWorkoutDatabase(application : Application) : WorkoutDatabase =
        WorkoutDatabase.getInstance(application)

    @Provides
    fun provideWorkoutRepository(application : Application) : WorkoutRepository {
        provideWorkoutDatabase(application).apply {
            return WorkoutRepositortImpl(
                this.exerciseSlotDao ,
                this.oneRepMaxDao ,
                this.setSlotDao ,
                this.weekDao ,
                this.workoutMetadataDao ,
                this.workoutSessionDao
            )
        }
    }
    @Provides
    fun provideProgressionManager() = ProgressionOverloadManager()

}