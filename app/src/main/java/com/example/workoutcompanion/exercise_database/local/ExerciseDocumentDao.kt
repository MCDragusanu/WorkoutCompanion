package com.example.workoutcompanion.exercise_library.data

import androidx.room.*

@Dao
interface ExerciseDocumentDao {

    @Insert
    suspend fun insertExercise(exerciseDocument: ExerciseDocument)

    @Update
    suspend fun updateExercise(exerciseDocument: ExerciseDocument)

    @Delete
    suspend fun deleteExercise(exerciseDocument: ExerciseDocument)

    @Query("SELECT * from exercise_document_table")
    suspend fun getExercises():List<ExerciseDocument>

    @Query("DELETE from exercise_document_table")
    suspend fun deleteExercises()

    @Query("SELECT (SELECT COUNT(*) from exercise_document_table) == 0")
    fun localDatabaseIsEmpty():Boolean
}