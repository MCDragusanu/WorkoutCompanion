package com.example.workoutcompanion.core.data.user_database.common

import android.util.Log
import com.example.workoutcompanion.core.data.auth_service.AuthManager
import com.example.workoutcompanion.core.data.user_database.cloud.CloudProfileRepository
import com.example.workoutcompanion.core.data.user_database.local.LocalProfileRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(private val cloudRepository : CloudProfileRepository,
                                                private val localRepository : LocalProfileRepository,
                                                val authManager:AuthManager
                                                ):ProfileRepository {


    override suspend fun addProfile(
        userUid : String ,
        userProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            Log.d("Test" , "Add profile completed")
            localRepository.addProfile(userProfile.uid , userProfile)
            cloudRepository.addProfile(userProfile.uid , userProfile)
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileFromLocalSource(
        userUid : String ,

    ) : Result<UserProfile?> {
        return try {
            localRepository.getProfileByUid(userUid )
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }


    override suspend fun deleteProfile(userUid : String) : Result<Nothing?> {
        return try {
            localRepository.deleteProfile(userUid)
            cloudRepository.deleteProfile(userUid)
            Result.success(null)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }


    override suspend fun updateLocalProfile(
        userUid : String ,
        userProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            localRepository.updateProfile(userProfile.uid , userProfile)
            Result.success(null)

        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCloudProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            cloudRepository.updateProfile(userUid , newProfile)
            Result.success(null)

        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileFromCloudSource(
        uid : String ,
        scope : CoroutineScope
    ) : Result<UserProfile?> {
        return try {
            val profile = cloudRepository.getProfileByUid(uid , scope).getOrNull()

            if (profile != null) {
                Result.success(profile)
            } else Result.failure(NullPointerException("There is no user that matches the uid ${uid}"))

        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


}