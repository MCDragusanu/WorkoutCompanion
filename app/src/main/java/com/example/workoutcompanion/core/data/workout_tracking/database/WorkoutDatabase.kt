package com.example.workoutcompanion.core.data.workout_tracking.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
import com.example.workoutcompanion.core.data.workout_tracking.workout_session.WorkoutSession
import com.example.workoutcompanion.core.data.workout_tracking.workout_session.WorkoutSessionDao

@Database(entities = [
    ExerciseSlot::class ,
    OneRepMax::class ,
    SetSlot::class ,
    Week::class ,
    WorkoutMetadata::class,
    WorkoutSession::class
                     ] ,
    version =1 )
abstract class WorkoutDatabase:RoomDatabase() {

    abstract val exerciseSlotDao : ExerciseSlotDao
    abstract val oneRepMaxDao : OneRepMaxDao
    abstract val setSlotDao : SetSlotDao
    abstract val weekDao : WeekDao
    abstract val workoutMetadataDao : WorkoutMetadataDao
    abstract val workoutSessionDao:WorkoutSessionDao

    companion object {
        private var instance : WorkoutDatabase? = null
        fun getInstance(application : Application) : WorkoutDatabase {
            return instance ?: Room.inMemoryDatabaseBuilder(
                application.applicationContext ,
                WorkoutDatabase::class.java ,

            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }
}