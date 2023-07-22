package com.example.workoutcompanion.core.data.user_database.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_profile_table")
data class UserProfile(@PrimaryKey
                       val uid:String,
                       val firstName:String,
                       val lastName:String,
                       val email:String,
                       val providerId:String,
                       val isEmailVerified:Boolean) {
    fun toMap() : Map<String , Any> {
        return mapOf(
            Pair("uid" , uid) ,
            Pair("firstName" , firstName) ,
            Pair("lastName" , lastName) ,
            Pair("email" , email) ,
            Pair("providerId" , providerId) ,
            Pair("isEmailVerified" , isEmailVerified)
        )
    }
}