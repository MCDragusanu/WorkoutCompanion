package com.example.workoutcompanion.core.data.workout.set_slot

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("set_slot_table")
data class SetSlot(
    val weightInKgs:Double ,
    val reps:Int ,
    val weekUid:Long ,
    val exerciseSlotUid:Long,
    val type:Int,
    val index:Int){
    @PrimaryKey(autoGenerate = true) var uid:Int = 0
    companion object{
       const val WarmUp = 0
       const val WorkingSet = 1
    }
}