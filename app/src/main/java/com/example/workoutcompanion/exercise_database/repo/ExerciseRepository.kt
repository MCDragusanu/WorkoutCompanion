package com.example.workoutcompanion.exercise_database.repo


import com.example.workoutcompanion.exercise_database.model.Exercise
import com.example.workoutcompanion.exercise_library.data.ExerciseDocument
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun addExerciseToDatabase(exerciseDocument: ExerciseDocument)

    suspend fun updateExerciseDocument(exerciseDocument: ExerciseDocument)

    suspend fun deleteExerciseDocument(exerciseDocument: ExerciseDocument)

    suspend fun deleteAllExercises()

    suspend fun databaseIsEmpty():Boolean

}