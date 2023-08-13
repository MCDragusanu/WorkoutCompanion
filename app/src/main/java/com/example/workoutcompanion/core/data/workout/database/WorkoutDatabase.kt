package com.example.workoutcompanion.core.data.workout.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlotDao
import com.example.workoutcompanion.core.data.workout.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout.one_rep_max.OneRepMaxDao
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlotDao
import com.example.workoutcompanion.core.data.workout.training_parameters.ProgressionSchema
import com.example.workoutcompanion.core.data.workout.training_parameters.ProgressionSchemaDao
import com.example.workoutcompanion.core.data.workout.training_parameters.TrainingParametersDao
import com.example.workoutcompanion.core.data.workout.training_parameters.TrainingParametersMetadata
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.week.WeekDao
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadataDao
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSessionDao

@Database(entities = [
    ExerciseSlot::class ,
    OneRepMax::class ,
    SetSlot::class ,
    Week::class ,
    WorkoutMetadata::class,
    WorkoutSession::class,
    ProgressionSchema::class,
    TrainingParametersMetadata::class] ,
    version = 1)
abstract class WorkoutDatabase:RoomDatabase() {

    abstract val exerciseSlotDao : ExerciseSlotDao
    abstract val oneRepMaxDao : OneRepMaxDao
    abstract val setSlotDao : SetSlotDao
    abstract val weekDao : WeekDao
    abstract val workoutMetadataDao : WorkoutMetadataDao
    abstract val workoutSessionDao:WorkoutSessionDao
    abstract val progressioDao:ProgressionSchemaDao
    abstract val trainingParametersDao:TrainingParametersDao

    companion object {
        private var instance : WorkoutDatabase? = null
        fun getInstance(application : Application) : WorkoutDatabase {
            return instance ?: Room.databaseBuilder(
                application.applicationContext ,
                WorkoutDatabase::class.java ,
                name = "workout_tracking_database"

            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }
}