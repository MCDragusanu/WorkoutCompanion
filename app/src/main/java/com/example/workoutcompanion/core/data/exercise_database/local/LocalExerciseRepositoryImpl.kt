package com.example.workoutcompanion.core.data.exercise_database.local

import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata
import com.example.workoutcompanion.core.data.exercise_database.common.DatabaseVersion
import javax.inject.Inject

class LocalExerciseRepositoryImpl @Inject constructor(private val exerciseDao : ExerciseDocumentDao ,
                                                      private val versionDao : VersionMetadataDao
                                                      ) : LocalExerciseRepository {
    override suspend fun addExercise(exerciseDocument : ExerciseDocument) : Result<Nothing?> {
        return try {
            exerciseDao.insertExercise(exerciseDocument)
            Result.success(null)
        } catch (e:Exception){
            Result.failure(e)
        }

    }

    override suspend fun updateExercise(exerciseDocument : ExerciseDocument) : Result<Nothing?> {
        return try {
            exerciseDao.updateExercise(exerciseDocument)
            Result.success(null)
        } catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun persistVersion(version : DatabaseVersion) : Result<Nothing?> {
        return try {
            versionDao.insertMetadata(version.metadata)
            version.content.onEach {
                exerciseDao.insertExercise(it)
            }
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearDatabase() : Result<Nothing?> {
       return try {
           versionDao.deleteAllMetadata()
           exerciseDao.deleteExercises()
           Result.success(null)
       }catch (e:Exception){
           Result.failure(e)
       }
    }

    override suspend fun getExerciseCollection() : Result<List<ExerciseDocument>> {
        return try {
            Result.success(exerciseDao.getExercises())
        } catch (e:Exception){
            Result.failure(e)
        }
    }

    override  suspend  fun getCurrentDatabaseVersion() : Result<VersionMetadata?> {
        return try {
            Result.success(versionDao.getCurrentMetadata())
        } catch (e:Exception){
            Result.failure(e)
        }
    }


}