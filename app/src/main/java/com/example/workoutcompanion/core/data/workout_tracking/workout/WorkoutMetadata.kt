package com.example.workoutcompanion.core.data.workout_tracking.workout

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose.colorMuscleGroups1
import kotlin.random.Random
import kotlin.random.nextInt

@Entity(tableName = "workout_table")
data class WorkoutMetadata(@PrimaryKey
                       val uid:Long ,
                           val ownerUid:String,
                           val name:String ,
                           val description:String,
                           val color : Int = colorMuscleGroups1[Random.nextInt((0 until colorMuscleGroups1.size) )].toArgb(),
                           val dayOfWeek:Int){

}