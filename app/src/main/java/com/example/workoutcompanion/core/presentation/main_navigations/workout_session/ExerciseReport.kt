package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot


data class ExerciseReport(val exerciseSlot : ExerciseSlot ,
                          val setsCompleted:Int,
                          val setsFailed:Int,
                          )
