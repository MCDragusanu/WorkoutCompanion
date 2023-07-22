package com.example.workoutcompanion.core.data.exercise_database.cloud

import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata
import com.example.workoutcompanion.core.data.exercise_database.common.DatabaseVersion
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDataSource
import com.example.workoutcompanion.core.data.firestore.FirebaseRepositoryImpl
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CloudDataSource:ExerciseDataSource {

    private val cloudRepo = FirebaseRepositoryImpl<DatabaseVersion> (Firebase.firestore.collection("Exercise Database"))

    override suspend fun getExerciseCollection() : Result<List<ExerciseDocument>> {
        return try {
            val task = cloudRepo.getCollection().orderBy(FieldPath.documentId()).get()
            task.await()
            if (task.exception != null) {
                Result.failure(task.exception!!)
            } else {
                val version = task.result.documents.first().toObject(DatabaseVersion::class.java)
                if (version == null) Result.failure(NullPointerException("Failed to convert content"))
                else {
                    Result.success(version.content)
                }

            }
        } catch (e : Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getCurrentDatabaseVersion() : Result<VersionMetadata?> {
        return try {
            val task = cloudRepo.getCollection().orderBy(FieldPath.documentId()).get()
            task.await()
            if (task.exception != null) {
                Result.failure(task.exception!!)
            } else {
                val version = task.result.documents.first().getField<VersionMetadata>("metadata")
                if (version == null) Result.failure(NullPointerException("Failed to convert metadata"))
                else {
                    Result.success(version)
                }

            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }


}