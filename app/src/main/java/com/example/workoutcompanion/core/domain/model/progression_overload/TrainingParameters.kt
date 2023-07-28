package com.example.workoutcompanion.workout_designer.progression_overload

data class TrainingParameters(val uid:Int,
                              val userUid:String,
                              val programUid:Long,
                              val primaryCompoundSchema: ExerciseProgressionSchema,
                              val secondaryCompoundSchema: ExerciseProgressionSchema,
                              val isolationSchema: ExerciseProgressionSchema
)