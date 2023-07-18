package com.example.workoutcompanion.exercise_database.version_control.repo_impl

import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersion
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionDataSource
import com.example.workoutcompanion.firestore.FirebaseRepository
import com.example.workoutcompanion.firestore.FirebaseRepositoryImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CloudDatabaseVersionDataSource(private val repo : FirebaseRepository<DatabaseVersion> =
                                       FirebaseRepositoryImpl(collectionReference = Firebase.firestore.collection("Exercise Collection Version"))):
    DatabaseVersionDataSource {


    override suspend fun getLatestVersion() : DatabaseVersion? {
        val task = repo.getCollection().orderBy("versionNumber").get()
        task.await()
        if (task.exception != null) {
            task.exception!!.printStackTrace()
            throw task.exception!!
        }
        return task.result.documents.first().toObject(DatabaseVersion::class.java)
    }

}