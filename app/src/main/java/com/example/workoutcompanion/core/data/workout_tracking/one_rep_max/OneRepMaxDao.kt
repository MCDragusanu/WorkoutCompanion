package com.example.workoutcompanion.core.data.workout_tracking.one_rep_max

import androidx.room.*


@Dao
interface OneRepMaxDao {


    @Insert
    suspend fun addOneRepMax(oneRepMax : OneRepMax)

    @Update
    suspend fun updateOneRepMax(oneRepMax : OneRepMax)

    @Delete
    suspend fun deleteOneRepMax(oneRepMax : OneRepMax)

    @Query("DELETE  from one_rep_max_table WHERE exerciseUid = :uid AND userUid =:userUid" )
    suspend fun deleteProgressionForExercise(uid:Int , userUid:String)

    @Query("SELECT * from one_rep_max_table WHERE exerciseUid = :uid AND userUid = :userUid ORDER BY timeStamp DESC")
    suspend fun getProgressionForExerciseInDescOrder(uid:Int , userUid:String):List<OneRepMax>

    @Query("SELECT * from one_rep_max_table WHERE exerciseUid = :uid ORDER BY timeStamp ASC")
    suspend fun getProgressionForExerciseInASCOrder(uid:Int):List<OneRepMax>

    @Query("SELECT * from one_rep_max_table WHERE exerciseUid = :uid AND userUid = :userUid AND (SELECT MAX(timeStamp) FROM one_rep_max_table)")
    suspend fun getLatestRecord(uid:Int , userUid : String):OneRepMax?


}