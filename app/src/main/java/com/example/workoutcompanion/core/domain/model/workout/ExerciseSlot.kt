package com.example.workoutcompanion.core.domain.model.workout

data class ExerciseSlot(val uid:Long ,
                        val workoutId:Long,
                        val exerciseId:Int,
                        val numberOfSets:Int,
                        val numberOfReps:Int,
                        val weightInKgs:Int,
                        )