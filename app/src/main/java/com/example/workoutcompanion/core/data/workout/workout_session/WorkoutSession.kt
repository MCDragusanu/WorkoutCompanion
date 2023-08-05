package com.example.workoutcompanion.core.data.workout.workout_session

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_session_table")
data class WorkoutSession(@PrimaryKey(autoGenerate = false)
                          val uid:Long ,
                          val ownerUid:String ,
                          val status:Int ,
                          val setUids:String
                          ){

    fun parseUids():List<Int>{
      return setUids.split("/").map { it.toInt() }
    }
    companion object{

        fun buildUidString(uidList:List<Int>):String{
             return buildString {
                  uidList.onEachIndexed { index , value->
                      append(value)
                      if(index!=uidList.indices.last)
                      append('/')
                  }
             }
        }
    }
}
