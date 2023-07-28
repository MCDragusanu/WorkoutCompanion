package com.example.workoutcompanion.core.data.exercise_database.common

import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata

interface ExerciseRepository {

    suspend fun getCloudDatabase():Result<List<ExerciseDocument>>

    suspend fun getCachedDatabase() :Result<List<ExerciseDocument>>

    suspend fun getCloudLatestVersion():Result<VersionMetadata?>

    suspend fun getCurrentVersionCached() :Result<VersionMetadata?>

    suspend fun onUpdateLocalDatabase() : Result<Nothing?>

    suspend fun getCachedExerciseByUid(uid:Int):Result<ExerciseDocument>
}