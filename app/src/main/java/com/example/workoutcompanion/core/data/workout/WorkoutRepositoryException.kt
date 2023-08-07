package com.example.workoutcompanion.core.data.workout

class WorkoutRepositoryException(val requestName:String , val reason:String):Exception("$reason has occurred when request =  $requestName")