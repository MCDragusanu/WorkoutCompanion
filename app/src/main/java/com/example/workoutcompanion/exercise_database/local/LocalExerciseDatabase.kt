package com.example.workoutcompanion.exercise_library.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersion
import com.example.workoutcompanion.exercise_database.version_control.DatabaseVersionDao

@Database(entities = [ExerciseDocument::class , DatabaseVersion::class] , version = 1)
abstract  class LocalExerciseDatabase : RoomDatabase(){

    abstract val exerciseDocumentDao : ExerciseDocumentDao
    abstract val versionDao:DatabaseVersionDao

    companion object{
        private var instance  : LocalExerciseDatabase? = null
        fun getInstance(context: Context):LocalExerciseDatabase{
            return instance?: Room.databaseBuilder(context.applicationContext , LocalExerciseDatabase::class.java  , "local_exercise_database").fallbackToDestructiveMigration().build()
        }
    }
}