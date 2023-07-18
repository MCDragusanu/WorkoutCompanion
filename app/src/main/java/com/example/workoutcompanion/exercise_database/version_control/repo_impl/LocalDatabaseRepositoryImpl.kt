package com.example.workoutcompanion.exercise_database.version_control.repo_impl

import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersion
import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersionDao
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionRepository
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(private val dao: DatabaseVersionDao):
    DatabaseVersionRepository  , DatabaseVersionDataSource {
    override suspend fun saveNewVersion(databaseVersion: DatabaseVersion) {
        dao.insertVersion(databaseVersion)
    }

    override suspend fun getLatestVersion(): DatabaseVersion? {
       return dao.getLatestVersion()
    }

    override suspend fun deleteOldVersions() {
        dao.deleteOldVersions()
    }

    override suspend fun deleteVersion(version : DatabaseVersion) {
        dao.deleteVersion(version)
    }
}