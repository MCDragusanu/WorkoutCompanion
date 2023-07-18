package com.example.workoutcompanion.on_board.domain

import com.example.workoutcompanion.common.Result
import com.example.workoutcompanion.on_board.data.profile.UserProfileRepository
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserProfileImpl :UserProfileRepository{
    private val profileCollection = Firebase.firestore.collection("User Collection")
    override suspend fun addNewUser(userProfile: UserProfile):Flow<com.example.workoutcompanion.common.Result> = flow {
        try {
            val task = profileCollection.document(userProfile.uid).set(userProfile)
            task.await()
            if(task.exception!=null){
                emit(Result.Error)
                throw task.exception!!
            }
            else emit(Result.Completed)
        }catch (e:FirebaseException){
            e.printStackTrace()
        }
    }

    override suspend fun getUser(firebaseUid: String): Flow<UserProfile>  = flow {
        try{
            val task = profileCollection.document(firebaseUid).get()
            task.await()
            if (task.exception != null)  throw task.exception!!
            else {
                val userProfile = task.result.toObject<UserProfile>()
                if (userProfile == null) throw NullPointerException("Failed to convert document to POJO")
                else emit(userProfile)
            }
        }catch (e:FirebaseException){
            e.printStackTrace()
        }
    }

    override suspend fun deleteUser(firebaseUid: String):Flow<com.example.workoutcompanion.common.Result> = flow {
        try {
            val task = profileCollection.document(firebaseUid).delete()
            task.await()
            if (task.exception != null) {
                emit(Result.Error)
                throw task.exception!!
            }
            else {
               emit(Result.Completed)
            }
        } catch (e: FirebaseException) {
            e.printStackTrace()
        }
    }

    override suspend fun updateUser(newUserProfile: UserProfile):Flow<com.example.workoutcompanion.common.Result> = flow{
        try {
            val task = profileCollection.document(newUserProfile.uid).set(newUserProfile)
            task.await()
            if (task.exception != null) {
                emit(Result.Error)
                throw task.exception!!
            }
            else {
                emit(Result.Completed)
            }
        } catch (e: FirebaseException) {
            e.printStackTrace()
        }
    }
}