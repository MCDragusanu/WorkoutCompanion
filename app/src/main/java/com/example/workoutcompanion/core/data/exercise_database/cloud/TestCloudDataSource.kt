package com.example.workoutcompanion.core.data.exercise_database.cloud

import android.util.Log
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDataSource
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.domain.model.exercise.exerciseList

class TestCloudDataSource:ExerciseDataSource {
    override suspend fun getExerciseCollection() : Result<List<ExerciseDocument>> {
        return Result.success(exerciseList.map { it.toDocument() })
    }

    override suspend fun getCurrentDatabaseVersion() : Result<VersionMetadata?> {

       return Result.success(VersionMetadata())
    }


}