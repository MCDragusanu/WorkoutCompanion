package com.example.workoutcompanion.core.data.user_database.cloud

import android.util.Log
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class TestCloudProfileRepository : ProfileRepository {



    override suspend fun getProfileByUid(
        uid : String ,
        scope : CoroutineScope
    ) : Result<UserProfile> {
        delay(2000)
        Log.d("Test" , "guest profile has been retrieved")
        return Result.success(guestProfile)
    }

    override suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        delay(2000)
        Log.d("Test" , "guest profile has been 'updated' ")
        return Result.success(null)
    }

    override suspend fun deleteProfile(userUid : String) : Result<Nothing?> {
        delay(2000)
        Log.d("Test" , "guest profile has been deleted")
        return Result.success(null)
    }

    override suspend fun addProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?> {
        delay(2000)
        return Result.success(null)
    }

}
