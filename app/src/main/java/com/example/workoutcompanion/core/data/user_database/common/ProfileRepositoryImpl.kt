package com.example.workoutcompanion.core.data.user_database.common

import android.util.Log
import com.example.workoutcompanion.core.data.auth_service.AuthManager
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(private val cloudRepository : ProfileRepository,
                                                private val localRepository : ProfileRepository,
                                                val authManager:AuthManager
                                                ) {


    suspend fun addProfile(userProfile : UserProfile):Result<Nothing?>{
        return try {
            Log.d("Test" , "Add profile completed")
            localRepository.addProfile(userProfile.uid , userProfile)
            cloudRepository.addProfile(userProfile.uid , userProfile)
            Result.success(null)
        }
        catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteProfile(userUid:String):Result<Nothing?> {
        return try {
            localRepository.deleteProfile(userUid)
            cloudRepository.deleteProfile(userUid)
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
    suspend fun updateProfile(userProfile : UserProfile):Result<Nothing?>{
        return try {
            localRepository.updateProfile(userProfile.uid , userProfile)
            cloudRepository.updateProfile(userProfile.uid , userProfile)
            Result.success(null)

        } catch (e : Exception) {
            Result.failure(e)
        }
    }


    suspend fun getCloudProfile(externalScope:CoroutineScope):Result<UserProfile?> {
        val uid = authManager.getCurrentUserUid() ?: return Result.failure(NullPointerException("There is no user signed in"))

        val profile = cloudRepository.getProfileByUid(uid , externalScope).getOrNull()

        if(profile!=null){
            localRepository.updateProfile(profile.uid , profile)
        }

        return Result.success(profile)
    }

}