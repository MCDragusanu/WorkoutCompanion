package com.example.workoutcompanion.on_board.domain

data class UserProfile(val uid:String,
                       val email:String,
                       val registerDate:String,
                       val provider:String,
                       val firstName:String,
                       val lastName:String,
                       val dateOfBirth:String,
                       val gender:String,
                       val weightInKgs:Float,
                       val heightInCms:Float,
                       val experienceLevel:String,

                       )