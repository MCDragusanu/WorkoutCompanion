package com.example.workoutcompanion.core.data.user_database.common

interface ProfileRepository: ProfileDataSource {

    suspend fun updateProfile(userUid:String , newProfile: UserProfile): Result<Nothing?>

    suspend fun deleteProfile(userUid : String):Result<Nothing?>

    suspend fun addProfile(userUid:String , newProfile : UserProfile):Result<Nothing?>
}