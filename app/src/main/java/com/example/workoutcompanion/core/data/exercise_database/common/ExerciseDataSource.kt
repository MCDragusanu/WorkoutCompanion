package com.example.workoutcompanion.core.data.exercise_database.common

import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata


interface ExerciseDataSource {

   suspend fun getExerciseCollection():Result<List<ExerciseDocument>>

   suspend fun getCurrentDatabaseVersion():Result<VersionMetadata?>


}