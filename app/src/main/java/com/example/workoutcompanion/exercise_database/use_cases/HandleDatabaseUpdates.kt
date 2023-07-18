package com.example.workoutcompanion.exercise_database.use_cases

import android.util.Log
import com.example.workoutcompanion.exercise_database.repo.ExerciseDataSource
import com.example.workoutcompanion.exercise_database.repo.ExerciseRepository
import com.example.workoutcompanion.exercise_database.repo.repo_impl.CloudExerciseDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionRepository
import kotlinx.coroutines.flow.collectLatest

class HandleDatabaseUpdates {
    suspend fun execute(
        exerciseRepository : ExerciseRepository,
        versionRepository : DatabaseVersionRepository,
        localVersionDataSource : DatabaseVersionDataSource,
        cloudVersionDataSource : DatabaseVersionDataSource,
        cloudExerciseDataSource : ExerciseDataSource,
        onSuccess : () -> Unit,
        onError : (Exception) -> Unit
    ) {
        try {
            //retrieve cloud version
            val cloudVersion = cloudVersionDataSource.getLatestVersion()
            //retrieve cached version
            val localVersion = localVersionDataSource.getLatestVersion()

            //check to see if there is available version on cloud
            if (cloudVersion == null) throw NullPointerException("No up-to-date version found")

            //check if there is no database cached
            if (localVersion == null) {
                versionRepository.saveNewVersion(cloudVersion)
                cacheCloudDatabase(exerciseRepository, cloudExerciseDataSource)
                Log.d("Test", "Latest version has been cached for the first time")
                onSuccess()
                return
            }

            //checking if it has an old version cached
            if (localVersion.versionNumber != cloudVersion.versionNumber) {
                versionRepository.deleteVersion(localVersion)
                versionRepository.saveNewVersion(cloudVersion)
                exerciseRepository.deleteAllExercises()
                cacheCloudDatabase(exerciseRepository, cloudExerciseDataSource)
                Log.d("Test", "Latest version has been cached")
                onSuccess()
                return
            }

            if (localVersion.versionNumber == cloudVersion.versionNumber) {
                Log.d("Test", "Latest version cached")
                onSuccess()
                return
            }
        } catch (e : Exception) {
            e.printStackTrace()
            onError(e)
        }

    }

    private suspend fun cacheCloudDatabase(
        repository : ExerciseRepository,
        dataSource : ExerciseDataSource
    ) {
        dataSource.getAllExercises().collectLatest {
            it.onEach {
                repository.addExerciseToDatabase(it)
            }
        }
    }
}