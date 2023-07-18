package com.example.workoutcompanion.exercise_database.version_control

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DatabaseVersionDao {

    @Insert
    suspend fun insertVersion(databaseVersion: DatabaseVersion)

    @Query("SELECT *" + "FROM database_version_table\n" + "WHERE dateOfPublish = (SELECT max(dateOfPublish) FROM database_version_table)")
    suspend fun getLatestVersion():DatabaseVersion?

    @Query("DELETE  from database_version_table where isDeprecated = true")
    suspend fun deleteOldVersions()

    @Delete
    suspend fun deleteVersion(version : DatabaseVersion)
}