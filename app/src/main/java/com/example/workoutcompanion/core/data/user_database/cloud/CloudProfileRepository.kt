package com.example.workoutcompanion.core.data.user_database.cloud

import com.example.workoutcompanion.core.data.firestore.FirebaseRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class CloudProfileRepository @Inject constructor(): ProfileRepository {

    private val repo =
        FirebaseRepositoryImpl<UserProfile>(Firebase.firestore.collection("User Profiles"))

    override suspend fun getProfileByUid(
        uid : String ,
        scope : CoroutineScope
    ) : Result<UserProfile> {
       return try {
           val profile = repo.getDocument(uid , UserProfile::class.java).stateIn(scope).value
           if(profile == null ) Result.failure(NullPointerException("No profile found"))
           else Result.success(profile)
       }catch (e:Exception){
           Result.failure(e)
       }
    }

    override suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            repo.updateDocument(userUid , newProfile.toMap())
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }

    }

    override suspend fun deleteProfile(userUid : String) : Result<Nothing?> {
        return try {
            repo.deleteDocument(userUid)
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            repo.createDocument(userUid , newProfile)
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}