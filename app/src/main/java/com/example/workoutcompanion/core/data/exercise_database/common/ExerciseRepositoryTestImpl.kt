package com.example.workoutcompanion.core.data.exercise_database.common

import android.util.Log
import com.example.workoutcompanion.core.data.exercise_database.local.LocalExerciseRepository
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata

class ExerciseRepositoryTestImpl(private val cloudDataSource : ExerciseDataSource , private  val localRepository :  LocalExerciseRepository) : ExerciseRepository {
    override suspend fun getCloudDatabase() : Result<List<ExerciseDocument>> {

       return cloudDataSource.getExerciseCollection()
    }

    override suspend fun getCachedDatabase() : Result<List<ExerciseDocument>> {

        return localRepository.getExerciseCollection()
    }

    override suspend fun getCloudLatestVersion() : Result<VersionMetadata?> {

       return cloudDataSource.getCurrentDatabaseVersion()
    }

    override suspend fun getCurrentVersionCached() : Result<VersionMetadata?> {

        return localRepository.getCurrentDatabaseVersion()
    }

    override suspend fun onUpdateLocalDatabase() : Result<Nothing?> {

        return try {
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
            } else {
                Log.d("Test" ,"Database is already up to date" )
            }
            Log.d("Test" , "Action Completed")
            Result.success(null)

        } catch (e : Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getCachedExerciseByUid(uid : Int) : Result<ExerciseDocument> {
        return try {
            Result.success(localRepository.getExerciseByUid(uid))
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}
