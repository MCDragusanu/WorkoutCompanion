package com.example.workoutcompanion.core.data.workout_tracking.week

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workoutcompanion.workout_designer.progression_overload.Weight
import java.util.UUID

@Entity("week_table")
data class Week(@PrimaryKey val uid:Long,
                val exerciseSlotUid:Long,
                val index:Int,
                val reps:Int ,
                val sets:Int ,
                val weightInKgs :Double){

}