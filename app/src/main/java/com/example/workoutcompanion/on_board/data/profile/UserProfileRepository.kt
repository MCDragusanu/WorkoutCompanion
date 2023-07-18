package com.example.workoutcompanion.on_board.data.profile

import com.example.workoutcompanion.on_board.domain.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    suspend fun addNewUser(userProfile: UserProfile):Flow<com.example.workoutcompanion.common.Result>

    suspend fun getUser(firebaseUid:String): Flow<UserProfile>

    suspend fun deleteUser(firebaseUid: String):Flow<com.example.workoutcompanion.common.Result>

    suspend fun updateUser(newUserProfile: UserProfile):Flow<com.example.workoutcompanion.common.Result>

}