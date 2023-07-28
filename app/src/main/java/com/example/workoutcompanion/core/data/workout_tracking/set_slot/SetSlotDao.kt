package com.example.workoutcompanion.core.data.workout_tracking.set_slot

import androidx.room.*

@Dao
interface SetSlotDao {

    @Insert
    suspend fun addSet(setSlot : SetSlot)

    @Delete
    suspend fun deleteSet(setSlot : SetSlot)

    @Update
    suspend fun updateSet(setSlot : SetSlot)

    @Query("DELETE from set_slot_table where weekUid = :weekUid ")
    suspend fun deleteAllSetsForWeek(weekUid:Long)

    @Query("SELECT * from set_slot_table where weekUid = :weekUid ORDER BY `index` ASC")
    suspend fun getAllSetsForWeek(weekUid:Long):List<SetSlot>

    @Query("UPDATE set_slot_table SET weightInKgs = :weightInKgs, reps = :reps,  `index`= :index WHERE uid = :uid")
    suspend fun updateSetSlot(weightInKgs:Double , reps:Int , index:Int, uid:Int)
}