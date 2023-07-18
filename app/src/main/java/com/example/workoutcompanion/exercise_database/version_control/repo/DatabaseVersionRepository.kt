package com.example.workoutcompanion.exercise_database.version_control.repo

import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersion

interface DatabaseVersionRepository {

    suspend fun saveNewVersion(databaseVersion: DatabaseVersion)

    suspend fun deleteOldVersions()

    suspend fun deleteVersion(version : DatabaseVersion)
}