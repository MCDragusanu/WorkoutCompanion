package com.example.workoutcompanion.core.data.user_database.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_profile_table")
data class UserProfile(@PrimaryKey
                       val uid:String = guestProfile.uid,
                       val firstName:String = guestProfile.firstName,
                       val lastName:String = guestProfile.lastName,
                       val email:String = guestProfile.email,
                       val providerId:String = guestProfile.providerId,
                       val isEmailVerified:Boolean = guestProfile.isEmailVerified) {
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