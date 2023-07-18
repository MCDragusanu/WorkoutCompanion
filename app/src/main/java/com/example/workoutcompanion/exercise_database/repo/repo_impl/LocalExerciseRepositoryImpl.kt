package com.example.workoutcompanion.exercise_database.repo.repo_impl

import com.example.workoutcompanion.exercise_database.model.Exercise
import com.example.workoutcompanion.exercise_database.repo.ExerciseRepository
import com.example.workoutcompanion.exercise_database.repo.ExerciseDataSource
import com.example.workoutcompanion.exercise_library.data.ExerciseDocument
import com.example.workoutcompanion.exercise_library.data.ExerciseDocumentDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalExerciseRepositoryImpl @Inject constructor(private val dao: ExerciseDocumentDao):
    ExerciseRepository, ExerciseDataSource {
    override suspend fun addExerciseToDatabase(exerciseDocument: ExerciseDocument) {
         dao.insertExercise(exerciseDocument)
    }

    override suspend fun updateExerciseDocument(exerciseDocument: ExerciseDocument) {
            dao.updateExercise(exerciseDocument)
    }

    override suspend fun deleteExerciseDocument(exerciseDocument: ExerciseDocument) {
            dao.deleteExercise(exerciseDocument)
    }

    override suspend fun getAllExercises(): Flow<List<Exercise>> = flow {
           emit(dao.getExercises().map { it.toExercise() })
    }

    override suspend fun deleteAllExercises() {
            dao.deleteExercises()
    }

    override suspend fun databaseIsEmpty(): Boolean {
       return dao.localDatabaseIsEmpty()
    }


}