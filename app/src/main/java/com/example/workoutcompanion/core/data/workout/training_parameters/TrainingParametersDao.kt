package com.example.workoutcompanion.core.data.workout.training_parameters

import androidx.room.*


@Dao
interface TrainingParametersDao {

    @Insert
    suspend fun addParameters(metadata : TrainingParametersMetadata)

    @Delete
    suspend fun deleteParameters(metadata : TrainingParametersMetadata)

    @Update
    suspend fun updateParameters(parametersMetadata : TrainingParametersMetadata)

    @Query("SELECT * from training_parameters_table where ownerUid = :userUid")
    suspend fun getParametersForUser(userUid:String):TrainingParametersMetadata?

 }