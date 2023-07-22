package com.example.workoutcompanion.core.data.exercise_database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata

@Database(entities = [ExerciseDocument::class , VersionMetadata::class ], version = 2)
 abstract  class  LocalExerciseDatabase:RoomDatabase(){
    abstract val exerciseDocumentDao : ExerciseDocumentDao
    abstract val versionDao: VersionMetadataDao

    companion object {
        private var instance : LocalExerciseDatabase? = null
        fun getInstance(context : Context) : LocalExerciseDatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext ,
                LocalExerciseDatabase::class.java ,
                "local_exercise_database"
            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }
}