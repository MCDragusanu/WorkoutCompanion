package com.example.workoutcompanion.core.data.workout

import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters

interface WorkoutRepository {

   suspend fun getLatestOneRepMax(uid : Int , userUid:String) : Result<OneRepMax?>
   suspend fun addWorkoutMetadata(workoutMetadata : WorkoutMetadata):Result<Nothing?>
   suspend fun addExerciseSlot(slot : ExerciseSlot):Result<Nothing?>
   suspend fun addWeek(startingPoint : Week):Result<Nothing?>
   suspend fun addSets(vararg  sets:SetSlot):Result<Nothing?>
   suspend fun getWorkouts(uid : String) :Result<List<WorkoutMetadata>>
   suspend fun getSlotsForWorkout(uid : Long) :Result<List<ExerciseSlot>>
   suspend fun getWeeksForSlot(slot : ExerciseSlot) : Result<List<Week>>
   suspend fun getSetsForWeek(week : Week):Result<List<SetSlot>>
   suspend fun addOneRepMax(oneRepMax : OneRepMax):Result<Nothing?>
   suspend fun updateWeek(newWeek : Week):Result<Nothing?>
   suspend fun updateSet(newSet : SetSlot):Result<Nothing?>
   suspend fun removeProgression(week : Week):Result<Nothing?>
   suspend fun getWorkoutByUid(_workoutUid : Long) :Result< WorkoutMetadata?>
   suspend  fun deleteExerciseSlot(slot : ExerciseSlot):Result<Nothing?>
   suspend fun updateMetadata(value : WorkoutMetadata):Result<Nothing?>
   suspend  fun removeSet(set : SetSlot):Result<Nothing?>
   suspend fun getTrainingParameters(userUid : String):Result<TrainingParameters?>
  suspend fun createInitialParameters(uid : String):Result<TrainingParameters>
  suspend fun updateSchema(schema : ExerciseProgressionSchema ,   parametersMetadata : Long):Result<Nothing?>
   suspend fun getLatestWeek(uid : Long) :Result<Week?>
   suspend fun addSession(session : WorkoutSession):Result<Nothing?>
   suspend fun getSession(sessionUid : Long):Result<WorkoutSession?>

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