package com.example.workoutcompanion.exercise_database.module

import com.example.workoutcompanion.exercise_database.repo.ExerciseDataSource
import com.example.workoutcompanion.exercise_database.repo.repo_impl.CloudExerciseDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo_impl.CloudDatabaseVersionDataSource
import com.example.workoutcompanion.firestore.FirebaseRepositoryImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudDatabaseModule {

    const val cloudExerciseDataSource = "Cloud Exercise Data Source"
    const val cloudDatabaseVersionDataSource ="CloudDatabaseVersionDataSource"
    @Provides
    @Singleton
    @Named(cloudExerciseDataSource)
    fun provideCloudExerciseDataSource():ExerciseDataSource{
        return CloudExerciseDataSource(FirebaseRepositoryImpl(collectionReference = Firebase.firestore.collection("Exercise Collection")))
    }


    @Provides
    @Singleton
    @Named(cloudDatabaseVersionDataSource)
    fun provideCloudDatabaseVersionDataSource() : DatabaseVersionDataSource = CloudDatabaseVersionDataSource()


}