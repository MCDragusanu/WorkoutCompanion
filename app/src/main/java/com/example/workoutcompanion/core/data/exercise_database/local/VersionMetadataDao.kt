package com.example.workoutcompanion.core.data.exercise_database.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata


@Dao
interface VersionMetadataDao {

    @Insert
    suspend fun insertMetadata(databaseVersion: VersionMetadata)

    @Query("SELECT *" + "FROM database_version_table\n" + "WHERE dateOfPublish = (SELECT max(dateOfPublish) FROM database_version_table)")
    suspend fun getCurrentMetadata(): VersionMetadata?

    @Query("DELETE from database_version_table")
    suspend fun deleteAllMetadata()

    @Delete
    suspend fun deleteMetadata(version : VersionMetadata)
}