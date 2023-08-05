package com.example.workoutcompanion.core.data.workout.week

import androidx.room.*

@Dao
interface WeekDao {

    @Insert
    suspend fun addWeek(week : Week)

    @Delete
    suspend fun deleteWeek(week : Week)

    @Update
    suspend fun udpateWeek(week : Week)

    @Query("Select * from week_table WHERE exerciseSlotUID = :uid ORDER BY `index` ASC")
    suspend fun getWeeksForSlotInASCOrder(uid:Long):List<Week>

    @Query("DELETE from week_table WHERE exerciseSlotUID = :uid")
    suspend fun deleteWeeksForSlot(uid:Long)

}