package com.example.workoutcompanion.core.data.exercise_database.common

import android.util.Log
import com.example.workoutcompanion.core.data.exercise_database.local.LocalExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


//TODO refactor auth module and user module to use annotations etc
 class ExerciseRepositoryImpl @Inject constructor(
    private val cloudDataSource: ExerciseDataSource ,
    private val localRepository: LocalExerciseRepository
):ExerciseRepository {

   override suspend fun getCloudDatabase() = cloudDataSource.getExerciseCollection()

    override suspend fun getCachedDatabase() = localRepository.getExerciseCollection()

    override suspend fun getCloudLatestVersion() = cloudDataSource.getCurrentDatabaseVersion()

    override suspend fun getCurrentVersionCached() = localRepository.getCurrentDatabaseVersion()

    override suspend fun onUpdateLocalDatabase() : Result<Nothing?> {

        return try {

            val localVersion = getCurrentVersionCached().getOrNull()

            val cloudVersion = getCloudLatestVersion().getOrNull() ?: return Result.failure(java.lang.NullPointerException("Database doesn't contain metadata"))


            if (localVersion == null || (localVersion.versionNumber != cloudVersion.versionNumber)) {

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
                    if (res.isFailure) {
                        return Result.failure(
                            res.exceptionOrNull()
                                ?: Exception("Unknown Error has occurred")
                        )
                    }
                }
            }
            Result.success(null)

        } catch (e : Exception) {
            Result.failure(e)
        }

    }
}

