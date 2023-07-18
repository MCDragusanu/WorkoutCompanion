package com.example.workoutcompanion.exercise_database.version_control.repo

import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersion

interface DatabaseVersionDataSource {
    suspend fun getLatestVersion(): DatabaseVersion?
}