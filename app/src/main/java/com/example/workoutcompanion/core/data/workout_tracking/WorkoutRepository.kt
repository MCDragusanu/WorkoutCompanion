package com.example.workoutcompanion.core.data.workout_tracking

import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout_tracking.workout_session.WorkoutSession

interface WorkoutRepository {

   suspend fun getLatestOneRepMax(uid : Int , userUid:String) : OneRepMax?
   suspend fun addWorkoutMetadata(workoutMetadata : WorkoutMetadata)
   suspend fun addExerciseSlot(slot : ExerciseSlot)
   suspend fun addWeek(startingPoint : Week)
   suspend fun addSets(vararg  sets:SetSlot)
   suspend fun getWorkouts(uid : String) : List<WorkoutMetadata>
   suspend fun getSlotsForWorkout(uid : Long) : List<ExerciseSlot>
   suspend fun getWeeksForSlot(slot : ExerciseSlot) : List<Week>
   suspend fun getSetsForWeek(week : Week):List<SetSlot>
   suspend fun addOneRepMax(oneRepMax : OneRepMax)
   suspend fun updateWeek(newWeek : Week)
   suspend fun updateSet(newSet : SetSlot)
   suspend fun removeProgression(week : Week)
   suspend fun getWorkoutByUid(_workoutUid : Long) : WorkoutMetadata?

    /*suspend fun addExerciseSlot(slot : ExerciseSlot) : Result<Nothing?>

    suspend fun addOneRepMax(oneRepMax : OneRepMax) : Result<Nothing?>

    suspend fun addSetSlot(setSlot : SetSlot) : Result<Nothing?>

    suspend fun addWeek(week : Week) : Result<Nothing?>

    suspend fun addWorkoutMetadata(workoutMetadata : WorkoutMetadata) : Result<Nothing?>

    suspend fun addSession(session : WorkoutSession) : Result<Nothing?>

    suspend fun updateExerciseSlot(slot : ExerciseSlot) : Result<Nothing?>

    suspend fun updateOneRepMax(oneRepMax : OneRepMax) : Result<Nothing?>

    suspend fun updateSetSlot(setSlot : SetSlot) : Result<Nothing?>

    suspend fun updateWeek(week : Week) : Result<Nothing?>

    suspend fun updateWorkoutMetadata(workoutMetadata : WorkoutMetadata) : Result<Nothing?>

    suspend fun updateSession(session : WorkoutSession) : Result<Nothing?>*/

}