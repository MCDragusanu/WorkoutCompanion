package com.example.workoutcompanion.core.data.workout_tracking

import android.util.Log
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlotDao
import com.example.workoutcompanion.core.data.workout_tracking.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout_tracking.one_rep_max.OneRepMaxDao
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlotDao
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.week.WeekDao
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadataDao
import com.example.workoutcompanion.core.data.workout_tracking.workout_session.WorkoutSessionDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WorkoutRepositoryImpl (private val exerciseSlotDao : ExerciseSlotDao ,
                             private val oneRepMaxDao : OneRepMaxDao ,
                             private val setSlotDao : SetSlotDao ,
                             private val weekDao : WeekDao ,
                             private val workoutMetadataDao : WorkoutMetadataDao ,
                             private val workoutSessionDao : WorkoutSessionDao
                             ):WorkoutRepository {
    override suspend fun getLatestOneRepMax(uid : Int , userUid:String) : OneRepMax? {
        return try {
            oneRepMaxDao.getLatestRecord(uid , userUid)
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun addWorkoutMetadata(workoutMetadata : WorkoutMetadata) {
        try {
            workoutMetadataDao.addNewWorkout(workoutMetadata)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addExerciseSlot(slot : ExerciseSlot) {
        try {
            exerciseSlotDao.addExerciseSlot(slot)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addWeek(week : Week) {
        try {
            weekDao.addWeek(week = week)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addSets(vararg sets : SetSlot) {
        CoroutineScope(Dispatchers.IO).launch {
           try {
                sets.onEach {
                    async { setSlotDao.addSet(it) }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override suspend fun getWorkouts(uid : String) : List<WorkoutMetadata> {
        return try {
            workoutMetadataDao.getWorkoutsOfUser(uid)
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getSlotsForWorkout(uid : Long) : List<ExerciseSlot> {
        return try {
            exerciseSlotDao.getSlotsForWorkout(uid)
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getWeeksForSlot(slot : ExerciseSlot) : List<Week> {
        return try {
            weekDao.getWeeksForSlotInASCOrder(slot.uid)
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getSetsForWeek(week : Week) : List<SetSlot> {
        return try {
            setSlotDao.getAllSetsForWeek(weekUid = week.uid)
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun addOneRepMax(oneRepMax : OneRepMax) {
        oneRepMaxDao.addOneRepMax(oneRepMax)
    }

    override suspend fun updateWeek(newWeek : Week) {
        Log.d("Test" , "week will be updated")
        weekDao.udpateWeek(newWeek)
    }

    override suspend fun updateSet(newSet : SetSlot) {
        Log.d("Test" , "set will be updated")
        try{
            setSlotDao.updateSetSlot(
                newSet.weightInKgs ,
                newSet.reps ,
                newSet.index ,
                uid = newSet.uid
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override suspend fun removeProgression(week : Week) {
        weekDao.deleteWeek(week = week)
    }

    override suspend fun getWorkoutByUid(_workoutUid : Long):WorkoutMetadata? {
        return try{
            workoutMetadataDao.getWorkoutByUid(_workoutUid)
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}