package com.example.workoutcompanion.core.data.workout.workout_session

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_session_table")
data class WorkoutSession(@PrimaryKey(autoGenerate = false)
                          val uid:Long ,
                          val workoutUid:Long,
                          val ownerUid:String ,
                          val status:Int ,
                          val slotList:String,
                          val cursorPosition : Long,
                          val setList:String
                          ){



    companion object {
        fun parseUids(content : String) : List<Long> {
            return content.split("/").filter { it.isNotBlank() }.map { it.toLong() }
        }

        fun buildUidString(uidList : List<Long>) : String {
            return buildString {
                uidList.onEachIndexed { index , value ->
                    append(value)
                    if (index != uidList.indices.last) append('/')
                }
            }
        }

        enum class SessionState {
            STARTED ,
            IN_PROGRESS ,
            COMPLETED;
        }
    }
}
