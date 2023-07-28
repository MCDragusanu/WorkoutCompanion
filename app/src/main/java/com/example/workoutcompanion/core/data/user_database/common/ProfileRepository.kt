package com.example.workoutcompanion.core.data.user_database.common

import kotlinx.coroutines.CoroutineScope

interface ProfileRepository: ProfileDataSource {

    suspend fun updateLocalProfile(userUid:String , newProfile: UserProfile): Result<Nothing?>

    suspend fun updateCloudProfile(userUid : String , newProfile : UserProfile):Result<Nothing?>

    suspend fun deleteProfile(userUid : String):Result<Nothing?>

    suspend fun addProfile(userUid:String , newProfile : UserProfile):Result<Nothing?>

    suspend fun getProfileFromLocalSource(userUid : String , externalScope:CoroutineScope):Result<UserProfile?>
}