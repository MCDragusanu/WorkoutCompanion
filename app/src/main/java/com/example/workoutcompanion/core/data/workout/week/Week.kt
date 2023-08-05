package com.example.workoutcompanion.core.data.workout.week

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("week_table")
data class Week(@PrimaryKey val uid:Long,
                val exerciseSlotUid:Long,
                val index:Int,
                val reps:Int ,
                val sets:Int ,
                val weightInKgs :Double){

}