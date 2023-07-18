package com.example.workoutcompanion.exercise_database.repo.repo_impl

import android.util.Log
import com.example.workoutcompanion.exercise_database.model.Exercise
import com.example.workoutcompanion.exercise_database.repo.ExerciseDataSource
import com.example.workoutcompanion.exercise_library.data.ExerciseDocument
import com.example.workoutcompanion.firestore.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject


class CloudExerciseDataSource @Inject constructor(private val repo:FirebaseRepository<ExerciseDocument>): ExerciseDataSource {
    override suspend fun getAllExercises() : Flow<List<Exercise>> = flow {
        val container = mutableListOf<ExerciseDocument>()
        val result = repo.getAllDocuments(ExerciseDocument::class.java).toList(container)
        emit(result.onEach { Log.d("Test" , it.exerciseName) }.map { it.toExercise() })
    }

}