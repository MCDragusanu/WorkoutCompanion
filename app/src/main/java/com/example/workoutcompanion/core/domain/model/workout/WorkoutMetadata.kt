package com.example.workoutcompanion.core.domain.model.workout

data class WorkoutMetadata(val uid:Long,
                           val ownerId:String,
                           val workoutName:String,
                           val scheduledDay:Int,
                           val scheduledHour:String
                           )
