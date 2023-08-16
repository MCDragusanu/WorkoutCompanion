package com.example.workoutcompanion.core.data.workout.set_slot

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import kotlin.math.absoluteValue


@Entity("set_slot_table")
data class SetSlot(
    @PrimaryKey
    var uid : Long = UUID.randomUUID().mostSignificantBits.absoluteValue,
    val weightInKgs:Double ,
    val reps:Int ,
    val weekUid:Long ,
    val exerciseSlotUid:Long,
    val type:Int,
    val status:Int,
    val index:Int) {




    enum class SetType {
        WarmUp ,
        WorkingSet;
    }
    enum class SetStatus{
        Failed,
        Default,
        Completed;
        fun isFailed() = this ==Failed
        fun isDefault() = this == Default

        fun Completed() = this == Completed
    }
}