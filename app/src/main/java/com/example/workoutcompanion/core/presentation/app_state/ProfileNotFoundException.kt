package com.example.workoutcompanion.core.presentation.app_state

class ProfileNotFoundException(userUid:String):Exception("Profile not found for user $userUid")