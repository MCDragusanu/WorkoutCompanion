package com.example.workoutcompanion.core.data.workout.exercise_slot

import androidx.room.*


@Dao
interface ExerciseSlotDao {

    @Insert
    suspend fun addExerciseSlot(slot : ExerciseSlot)

    @Delete
    suspend fun deleteSlot(slot : ExerciseSlot)

    @Update
    suspend fun updateSlot(slot : ExerciseSlot)

    @Query("Select * from exercise_slot_table where workoutUid = :workoutUid")
    suspend fun getSlotsForWorkout(workoutUid:Long):List<ExerciseSlot>

    @Query("Select * from exercise_slot_table where uid = :uid")
    suspend fun getSlotById(uid:Long): ExerciseSlot?

    @Query("DELETE from exercise_slot_table where uid = :uid")
    suspend fun deleteSlotByUid(uid : Long)

    @Query("DELETE from exercise_slot_table where workoutUid = :uid")
    suspend fun clearWorkoutSlots(uid:Long)
}