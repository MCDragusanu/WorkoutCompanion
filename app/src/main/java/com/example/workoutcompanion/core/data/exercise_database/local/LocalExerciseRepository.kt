package com.example.workoutcompanion.core.data.exercise_database.local

import com.example.workoutcompanion.core.data.exercise_database.common.DatabaseVersion
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDataSource
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument

interface LocalExerciseRepository: ExerciseDataSource {

    suspend fun addExercise(exerciseDocument : ExerciseDocument):Result<Nothing?>

    suspend fun updateExercise(exerciseDocument: ExerciseDocument):Result<Nothing?>

    suspend fun persistVersion(version : DatabaseVersion):Result<Nothing?>

    suspend fun clearDatabase():Result<Nothing?>

    suspend fun getExerciseByUid(uid:Int):ExerciseDocument

}