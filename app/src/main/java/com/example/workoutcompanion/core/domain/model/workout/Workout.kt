package com.example.workoutcompanion.core.domain.model.workout

import com.example.workoutcompanion.core.domain.model.exercise.Exercise

data class Workout(val metadata : WorkoutMetadata , val exerciseList:List<Exercise>)
