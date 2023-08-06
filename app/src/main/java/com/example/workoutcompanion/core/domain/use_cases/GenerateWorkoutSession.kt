package com.example.workoutcompanion.core.domain.use_cases

import android.util.Log
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession

class GenerateWorkoutSession {

    suspend fun execute(
        workoutMetadata : WorkoutMetadata ,
        workoutRepository : WorkoutRepository
    ) : Result<WorkoutSession> {
        return try {
            val slots = workoutRepository.getSlotsForWorkout(workoutMetadata.uid)
            val latestWeeks = mutableListOf<Week>()
            var string = ""
            slots.onEach {
                val latestWeek = workoutRepository.getLatestWeek(it.uid)

                val sets = workoutRepository.getSetsForWeek(latestWeek)

                string += buildString {
                    append("{")
                    append("slotUid:${it.uid}")
                    append("-")
                    sets.sortedBy { it.index }.onEach {
                        append("setUid:${it.uid}")
                        append("-")
                    }
                    append("}")
                }
            }
            Log.d("Test" , "Session content = $string")
            Result.success(
                WorkoutSession(
                    uid = System.currentTimeMillis() ,
                    ownerUid = workoutMetadata.ownerUid ,
                    status = 0 ,
                    content = string
                )
            )
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}