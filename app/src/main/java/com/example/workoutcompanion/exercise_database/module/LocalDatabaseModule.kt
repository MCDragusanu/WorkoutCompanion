package com.example.workoutcompanion.exercise_database.module

import android.app.Application
import com.example.workoutcompanion.exercise_database.repo.ExerciseDataSource
import com.example.workoutcompanion.exercise_database.repo.ExerciseRepository
import com.example.workoutcompanion.exercise_database.repo.repo_impl.LocalExerciseRepositoryImpl
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionRepository
import com.example.workoutcompanion.exercise_database.version_control.repo_impl.LocalDatabaseRepositoryImpl
import com.example.workoutcompanion.exercise_library.data.LocalExerciseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.checkerframework.common.value.qual.PolyValue
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {


    const val localExerciseDataSource = "Local Exercise Data Source"
    const val localDatabaseVersionDataSource = "Local Database Version Data Source"
    @Provides
    @Singleton
    fun provideLocalExerciseDatabase(application : Application) : LocalExerciseDatabase {
        return LocalExerciseDatabase.getInstance(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideLocalExerciseRepository(application : Application) : ExerciseRepository {
        return LocalExerciseRepositoryImpl(provideLocalExerciseDatabase(application).exerciseDocumentDao)
    }


    @Provides
    @Singleton
    @Named(localExerciseDataSource)
    fun provideLocalExerciseDataSource(application : Application) : ExerciseDataSource {
        return LocalExerciseRepositoryImpl(provideLocalExerciseDatabase(application).exerciseDocumentDao)
    }

    @Provides
    @Singleton
    fun provideDatabaseVersionRepo(application : Application) : DatabaseVersionRepository {
        return LocalDatabaseRepositoryImpl(provideLocalExerciseDatabase(application).versionDao)
    }

    @Provides
    @Singleton
    @Named(localDatabaseVersionDataSource)
    fun provideLocalVersionDataSource(application : Application) : DatabaseVersionDataSource =
        LocalDatabaseRepositoryImpl(provideLocalExerciseDatabase(application).versionDao)
}