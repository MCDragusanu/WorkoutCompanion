package com.example.workoutcompanion.core.data.exercise_database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.data.exercise_database.version_control.common.VersionMetadata

@Database(entities = [ExerciseDocument::class , VersionMetadata::class ], version = 1)
abstract  class  TestExerciseDatabase: RoomDatabase(){
    abstract val exerciseDocumentDao : ExerciseDocumentDao
    abstract val versionDao: VersionMetadataDao

    companion object{
        private var instance  : TestExerciseDatabase? = null
        fun getInstance(context: Context): TestExerciseDatabase {
            return instance?:Room.inMemoryDatabaseBuilder(
                context.applicationContext ,
                TestExerciseDatabase::class.java).fallbackToDestructiveMigration().build().apply {
                    instance=this
            }
        }
    }
}