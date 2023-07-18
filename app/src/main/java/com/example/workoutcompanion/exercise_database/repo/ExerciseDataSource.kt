package com.example.workoutcompanion.exercise_database.repo

import com.example.workoutcompanion.exercise_database.model.Exercise
import com.example.workoutcompanion.exercise_library.data.ExerciseDocument
import kotlinx.coroutines.flow.Flow

interface ExerciseDataSource {
    suspend fun getAllExercises(): Flow<List<ExerciseDocument>>
}