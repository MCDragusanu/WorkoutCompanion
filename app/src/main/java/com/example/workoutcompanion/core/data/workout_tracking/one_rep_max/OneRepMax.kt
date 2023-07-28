package com.example.workoutcompanion.core.data.workout_tracking.one_rep_max

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "one_rep_max_table")
data class OneRepMax( val exerciseUid:Int ,val userUid:String, val weightKgs:Double , val timeStamp:Long){
    @PrimaryKey(autoGenerate = true) var uid:Int = 0
}
