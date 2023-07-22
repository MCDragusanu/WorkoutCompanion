package com.example.workoutcompanion.core.data.exercise_database.common

import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata
import com.example.workoutcompanion.core.domain.model.exerciseList

class DatabaseVersion(val metadata : VersionMetadata = VersionMetadata() ,
                      val content:List<ExerciseDocument> = exerciseList.map { it.toDocument() }) {
}