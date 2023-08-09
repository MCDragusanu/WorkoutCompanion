package com.example.workoutcompanion.core.presentation.app_state

class TrainingParametersNotFound(userUid:String):Exception("Training parameters not found for user ${userUid}")