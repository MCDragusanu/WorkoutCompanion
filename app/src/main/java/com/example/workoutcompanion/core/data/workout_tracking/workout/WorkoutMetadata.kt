package com.example.workoutcompanion.core.data.workout_tracking.workout

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose.workoutTagColorPairs
import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.random.nextInt

@Entity(tableName = "workout_table")
data class WorkoutMetadata(@PrimaryKey
                       val uid:Long ,
                           val ownerUid:String ,
                           val name:String ,
                           val description:String ,
                           val programLengthInWeeks :Int = 20,
                           val gradientStart : Int = workoutTagColorPairs[Random.nextInt().absoluteValue% workoutTagColorPairs.size].first.toArgb() ,
                           val gradientEnd : Int = workoutTagColorPairs[Random.nextInt().absoluteValue% workoutTagColorPairs.size].second.toArgb() ,
                           val dayOfWeek:Int){


}