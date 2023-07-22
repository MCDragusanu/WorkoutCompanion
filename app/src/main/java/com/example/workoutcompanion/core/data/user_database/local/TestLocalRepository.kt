package com.example.workoutcompanion.core.data.user_database.local

import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.di.Testing
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TestLocalRepository @Inject constructor(@Testing private val dao : UserDao):ProfileRepository {
    override suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {
            dao.updateProfile(userProfile = newProfile)
            Result.success(null)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(userUid : String) : Result<Nothing?> {
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
            dao.addProfile(userProfile = newProfile)
            Result.success(null)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProfileByUid(
        uid : String ,
        scope : CoroutineScope
    ) : Result<UserProfile> {
        return try {
            val user = dao.getProfileByUid(uid)
            if(user == null) Result.failure(java.lang.NullPointerException("No user found"))
            else Result.success(user)
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}