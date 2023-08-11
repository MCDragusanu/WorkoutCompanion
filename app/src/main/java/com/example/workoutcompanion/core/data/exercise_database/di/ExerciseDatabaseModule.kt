package com.example.workoutcompanion.core.data.exercise_database.di

import android.app.Application
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.di.Testing

import com.example.workoutcompanion.core.data.exercise_database.cloud.CloudDataSource
import com.example.workoutcompanion.core.data.exercise_database.cloud.TestCloudDataSource
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDataSource
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepositoryImpl
import com.example.workoutcompanion.core.data.exercise_database.local.LocalExerciseDatabase
import com.example.workoutcompanion.core.data.exercise_database.local.LocalExerciseRepositoryImpl
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepository
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepositoryTestImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExerciseDatabaseModule {


    @Provides
    @Singleton
    fun provideExerciseDatabase(application : Application) : LocalExerciseDatabase =
        LocalExerciseDatabase.getInstance(application.applicationContext)

    @Provides
    @Singleton
   @Production
    fun provideCloudDataSource() : ExerciseDataSource = CloudDataSource()

    @Provides
    @Singleton
   @Testing
    fun provideTestCloudDataSource() : ExerciseDataSource = TestCloudDataSource()




    @Singleton
    @Provides
   @Production
    fun provideExerciseRepository(application : Application) : ExerciseRepository {
        provideExerciseDatabase(application).apply {
            return ExerciseRepositoryImpl(
                cloudDataSource = CloudDataSource() ,
                localRepository = LocalExerciseRepositoryImpl(
                    this.exerciseDocumentDao ,
                    this.versionDao
                )
            )
        }
    }

    @Provides
    @Singleton
   @Testing
    fun provideTestExerciseRepository(application : Application) : ExerciseRepository {
        provideExerciseDatabase(application).apply {
            return ExerciseRepositoryTestImpl(
                cloudDataSource = TestCloudDataSource() ,
                localRepository = LocalExerciseRepositoryImpl(
                    this.exerciseDocumentDao ,
                    this.versionDao
                )
            )
        }
    }
}