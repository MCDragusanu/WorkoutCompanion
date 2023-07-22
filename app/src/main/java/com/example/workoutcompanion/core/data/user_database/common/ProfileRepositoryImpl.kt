package com.example.workoutcompanion.core.data.user_database.common

import android.util.Log
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(private val cloudRepository : ProfileRepository , private val localRepository : ProfileRepository) {


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
}