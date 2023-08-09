package com.example.workoutcompanion.core.data.user_database.local

import android.util.Log
import com.example.workoutcompanion.core.data.di.ComponentType
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import javax.inject.Inject


class LocalProfileRepositoryTestImpl @Inject constructor(@ComponentType(true) private val dao : UserDao):LocalProfileRepository {
    override suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        return try {

            //dao.updateProfile(userProfile = newProfile)

            Result.success(null)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(userUid : String) : Result<Nothing?> {
        return try {
          //  dao.deleteProfile(userUid)
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
       // scope : CoroutineScope
    ) : Result<UserProfile> {
        return try {
            val user = guestProfile //dao.getProfileByUid(uid)

            Log.d("Test" , "profile has been retrieved from local soruce with uid= ${user.uid}")
            Result.success(user)

        }catch (e:Exception){
            Result.failure(e)
        }
    }
}