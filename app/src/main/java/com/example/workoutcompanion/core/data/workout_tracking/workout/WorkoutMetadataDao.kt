package com.example.workoutcompanion.core.data.workout_tracking.workout

import androidx.room.*

@Dao
interface WorkoutMetadataDao {

    @Insert
    suspend fun addNewWorkout(workoutMetadata : WorkoutMetadata)


    @Delete
    suspend fun deleteWorkout(workoutMetadata : WorkoutMetadata)

    @Update
    suspend fun updateWorkout(workoutMetadata : WorkoutMetadata)

    @Query("SELECT * from workout_table where ownerUid = :userUid")
    suspend fun getWorkoutsOfUser(userUid:String):List<WorkoutMetadata>

    @Query("DELETE from workout_table where ownerUid = :userUid ")
    suspend fun deleteAlLWorkoutsForUser(userUid : String)
    @Query("SELECT * from workout_table where uid = :workoutUid")
    fun getWorkoutByUid(workoutUid : Long) : WorkoutMetadata?

}