package com.example.workoutcompanion.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_account_table")
data class AccountInformation(
    @PrimaryKey
    val account_uid:String = "",
    val email:String,
    val firstName:String,
    val lastName:String,
    val provider:String = "EMAIL",
    val gender:Int,
    val dateOfBirth: String,
    val accountCreationDate: String,
    val lastEntry: String,
    val rememberMe:Boolean,
    val heightInCms:Float = 175f,
    val weightInKgs:Float = 75f,
    val trainingExperience:Int,
    val unitOfMeasureHeight:Int,
    val unitOfMeasurementWeight:Int)
