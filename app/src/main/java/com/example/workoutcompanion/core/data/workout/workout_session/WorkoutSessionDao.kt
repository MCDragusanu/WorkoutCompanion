package com.example.workoutcompanion.core.data.workout.workout_session

import androidx.room.*


@Dao
interface WorkoutSessionDao {

    @Insert
    suspend fun addSession(session : WorkoutSession)

    @Delete
    suspend fun deleteSession(session : WorkoutSession)

    @Update
    suspend fun updateSession(session : WorkoutSession)

    @Query("SELECT * from workout_session_table where ownerUid = :ownerUid ")
    suspend fun getAllSessionsForUser(ownerUid : String):List<WorkoutSession>

    @Query("DELETE from workout_session_table where ownerUid = :ownerUid")
    suspend fun deleteAllSessionsForUser(ownerUid:String)
}