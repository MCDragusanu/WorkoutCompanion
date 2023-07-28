package com.example.workoutcompanion.core.data.workout_tracking.set_slot

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("set_slot_table")
data class SetSlot(
    val weightInKgs:Double ,
    val reps:Int ,
    val weekUid:Long ,
    val index:Int){
    @PrimaryKey(autoGenerate = true) var uid:Int = 0
}