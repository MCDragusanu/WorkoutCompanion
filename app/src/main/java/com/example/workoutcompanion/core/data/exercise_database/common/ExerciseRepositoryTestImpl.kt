package com.example.workoutcompanion.core.data.exercise_database.common

import android.util.Log
import com.example.workoutcompanion.core.data.exercise_database.local.LocalExerciseRepository
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata
import com.example.workoutcompanion.core.data.user_database.local.TestLocalRepository

class ExerciseRepositoryTestImpl(private val cloudDataSource : ExerciseDataSource , private  val localRepository :  LocalExerciseRepository) : ExerciseRepository {
    override suspend fun getCloudDatabase() : Result<List<ExerciseDocument>> {
       Log.d("Test" , "@ExerciseRepositoryTestImpl.getCloudDatabase-> invoked")
       return cloudDataSource.getExerciseCollection()
    }

    override suspend fun getCachedDatabase() : Result<List<ExerciseDocument>> {
        Log.d("Test" , "@ExerciseRepositoryTestImpl.getCachedDatabase-> invoked")
        return localRepository.getExerciseCollection()
    }

    override suspend fun getCloudLatestVersion() : Result<VersionMetadata?> {
        Log.d("Test" , "@ExerciseRepositoryTestImpl.getCloudLatestVersion-> invoked")
       return cloudDataSource.getCurrentDatabaseVersion()
    }

    override suspend fun getCurrentVersionCached() : Result<VersionMetadata?> {
        Log.d("Test" , "@ExerciseRepositoryTestImpl.getCurrentVersionCached-> invoked")
        return localRepository.getCurrentDatabaseVersion()
    }

    override suspend fun onUpdateLocalDatabase() : Result<Nothing?> {

        return try {
            Log.d("Test" , "@ExerciseRepositoryTestImpl.onUpdateLocalDatabase-> started")
            val localVersion = getCurrentVersionCached().getOrNull()

            val cloudVersion = getCloudLatestVersion().getOrNull()
                ?: return Result.failure(java.lang.NullPointerException("Database doesn't contain metadata"))


            if (localVersion == null || (localVersion.versionNumber != cloudVersion.versionNumber)) {
                Log.d("Test" , "Database must be updated")
                val task = localRepository.clearDatabase()

                if (task.isFailure) return Result.failure(
                    task.exceptionOrNull() ?: Exception("Unknown Error has occurred")
                )

                val fetch = getCloudDatabase()

                if (fetch.isFailure) return Result.failure(
                    fetch.exceptionOrNull() ?: Exception("Unknown Error has occurred")
                )


                fetch.getOrNull()?.let {
                    val res = localRepository.persistVersion(DatabaseVersion(cloudVersion , it))
                    if (res.isSuccess) {
                        Log.d("Test" , "Database has been updated")
                    } else {
                        return Result.failure(
                            res.exceptionOrNull()
                                ?: Exception("Unknown Error has occurred")
                        )
                    }

                }
            }
            Log.d("Test" , "Action Completed")
            Result.success(null)

        } catch (e : Exception) {
            Result.failure(e)
        }

    }

}
