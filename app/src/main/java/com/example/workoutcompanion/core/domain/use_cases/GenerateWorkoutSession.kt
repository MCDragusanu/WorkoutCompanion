package com.example.workoutcompanion.core.domain.use_cases

import android.util.Log
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession

class GenerateWorkoutSession {

    fun buildSession(workoutMetadata : WorkoutMetadata,
                     slotList:List<ExerciseSlot>,
                     setList:List<SetSlot>
    ):WorkoutSession {
        val uid = System.currentTimeMillis()
        val ownerUid = workoutMetadata.ownerUid
        val status = WorkoutSession.Companion.SessionState.STARTED.ordinal
        val slots = WorkoutSession.buildUidString(slotList.map { it.uid })
        val sets = WorkoutSession.buildUidString(setList.map { it.uid.toLong() })
        return WorkoutSession(
            uid = uid ,
            workoutUid = workoutMetadata.uid,
            ownerUid = ownerUid ,
            status = status ,
            slotList = slots ,
            cursorPosition = setList.firstOrNull()?.uid?:0 ,
            setList = sets
        )
    }
}