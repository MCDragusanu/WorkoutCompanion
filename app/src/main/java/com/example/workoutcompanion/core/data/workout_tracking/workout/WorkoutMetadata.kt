package com.example.workoutcompanion.core.data.workout_tracking.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class WorkoutMetadata(@PrimaryKey
                       val uid:Long ,
                           val ownerUid:String,
                           val name:String ,
                           val description:String ,
                           val dayOfWeek:Int)