package com.example.workoutcompanion.workout_designer.progression_overload

import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema

data class TrainingParameters(val uid:Long ,
                              val userUid:String ,
                              val programUid:Long ,
                              val primaryCompoundSchema: ExerciseProgressionSchema ,
                              val secondaryCompoundSchema: ExerciseProgressionSchema ,
                              val isolationSchema: ExerciseProgressionSchema
)