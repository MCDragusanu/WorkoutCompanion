package com.example.workoutcompanion.core.data.workout.training_parameters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_parameters_table")
data class TrainingParametersMetadata(@PrimaryKey val uid:Long, val name:String  , val description:String , val ownerUid:String , val warmUpWeekCount:Int = 2 , val accumulationWeekCount : Int = 10, val deloadWeekCount:Int = 3  )