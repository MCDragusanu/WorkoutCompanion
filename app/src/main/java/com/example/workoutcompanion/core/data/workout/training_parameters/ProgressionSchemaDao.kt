package com.example.workoutcompanion.core.data.workout.training_parameters

import androidx.room.*


@Dao
interface ProgressionSchemaDao {

    @Insert
    suspend fun addSchema(vararg schema : ProgressionSchema)

    @Delete
    suspend fun deleteSchema(schema:ProgressionSchema)

    @Update
    suspend fun updateSchema(schema : ProgressionSchema)

    @Query("Select * from progression_schema_table where metadataUid =:uid")
    suspend fun getSchemas(uid:Long):List<ProgressionSchema>


}