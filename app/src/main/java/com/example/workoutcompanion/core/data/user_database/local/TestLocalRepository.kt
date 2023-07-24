package com.example.workoutcompanion.core.data.user_database.local

import android.util.Log
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TestLocalRepository @Inject constructor(@Testing private val dao : UserDao):ProfileRepository {
    override suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {

            //dao.updateProfile(userProfile = newProfile)
            Log.d("Test" , "profile has been updated with uid= ${guestProfile.uid}")
            Result.success(null)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(userUid : String) : Result<Nothing?> {
        return try {
          //  dao.deleteProfile(userUid)
            Log.d("Test" , "profile has been deleted with uid= $userUid")
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
            //dao.addProfile(userProfile = newProfile)
            Log.d("Test" , "profile has been added with uid= $newProfile")
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
            val user = guestProfile //dao.getProfileByUid(uid)
            if(user == null) {
                Log.d("Test" , "No user was found matching $uid")
                Result.failure(java.lang.NullPointerException("No user found"))
            }
            else {
                Log.d("Test" , "profile has been retrieved from local soruce with uid= ${user.uid}")
                Result.success(user)
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}