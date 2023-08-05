package com.example.workoutcompanion.core.data.workout.training_parameters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progression_schema_table")
data class ProgressionSchema (val repLowerBound:Int ,
                              val metadataUid:Long ,
                              val repUpperBound : Int ,
                              val appliedTo:Int,
                              val weightIncrementPercent:Double ,
                              val repIncreaseRate:Int ,
                              val smallestWeightIncrementAvailable:Double){
   @PrimaryKey(autoGenerate = true) var uid:Int = 0
}