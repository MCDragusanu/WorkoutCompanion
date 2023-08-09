package com.example.workoutcompanion.core.data.user_database.local

import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import kotlinx.coroutines.CoroutineScope

interface LocalProfileRepository {
    suspend fun getProfileByUid(uid : String ,
                               /* scope : CoroutineScope*/
    ) : Result<UserProfile?>

    suspend fun updateProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?>

    suspend fun deleteProfile(userUid : String) : Result<Nothing?>

    suspend fun addProfile(
        userUid : String ,
        newProfile : UserProfile
    ) : Result<Nothing?>

}