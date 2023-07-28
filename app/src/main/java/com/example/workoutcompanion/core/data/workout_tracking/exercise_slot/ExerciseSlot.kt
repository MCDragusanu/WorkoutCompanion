package com.example.workoutcompanion.core.data.workout_tracking.exercise_slot

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.exercise.MuscleGroup

@Entity("exercise_slot_table")
data class ExerciseSlot(@PrimaryKey
                        val uid:Long,
                        val exerciseName:String,
                        val type:Int,
                        val category:Int,
                        val isBodyWeight :Boolean,
                        val muscleGroups:String,
                        val workoutUid:Long,
                        val exerciseUid:Int,
                        val index:Int,
                        )