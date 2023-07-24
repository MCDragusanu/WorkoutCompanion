package com.example.workoutcompanion.core.data.exercise_database.cloud

import android.util.Log
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDataSource
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.domain.model.exercise.exerciseList

class TestCloudDataSource:ExerciseDataSource {
    override suspend fun getExerciseCollection() : Result<List<ExerciseDocument>> {
        Log.d("Test" , "@TestCloudDataSource.getExerciseCollection::Provided in memory exercise list")
        return Result.success(exerciseList.map { it.toDocument() })
    }

    override suspend fun getCurrentDatabaseVersion() : Result<VersionMetadata?> {
        Log.d("Test" , "@TestCloudDataSource.getExerciseCollection::Provided Default Version")
       return Result.success(VersionMetadata())
    }


}