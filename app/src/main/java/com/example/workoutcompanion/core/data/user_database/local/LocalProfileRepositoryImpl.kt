package com.example.workoutcompanion.core.data.user_database.local

import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class LocalProfileRepositoryImpl @Inject constructor (private val dao : UserDao) :
    LocalProfileRepository {
    override suspend fun getProfileByUid(uid : String , scope : CoroutineScope) : Result<UserProfile?> {
       return try {
           val profile = dao.getProfileByUid(uid)
           Result.success(profile)

       }catch (e:Exception){
           Result.failure(e)
       }
    }

    override suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?>{
        return try {
            dao.updateProfile(newProfile)

            Result.success(null)

        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(userUid : String) :  Result<Nothing?> {
        return try {
            dao.deleteProfile(userUid)
            Result.success(null)

        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun addProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            dao.addProfile(newProfile)
            Result.success(null)
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}