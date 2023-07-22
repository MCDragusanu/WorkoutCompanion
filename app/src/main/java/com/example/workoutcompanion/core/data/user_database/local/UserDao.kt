package com.example.workoutcompanion.core.data.user_database.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.workoutcompanion.core.data.user_database.common.UserProfile


@Dao
interface UserDao {

    @Query("SELECT * from user_profile_table WHERE uid = :uid")
    suspend fun getProfileByUid(uid:String): UserProfile?

    @Insert
    suspend fun addProfile(userProfile : UserProfile)

    @Query("DELETE from user_profile_table where uid = :uid")
    suspend fun deleteProfile(uid:String)

    @Update
    suspend fun updateProfile(userProfile : UserProfile)
}