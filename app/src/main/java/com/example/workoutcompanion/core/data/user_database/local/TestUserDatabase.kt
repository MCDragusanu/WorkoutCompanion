package com.example.workoutcompanion.core.data.user_database.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutcompanion.core.data.user_database.common.UserProfile

@Database(entities = [UserProfile::class] , version = 1)
abstract class TestUserDatabase: RoomDatabase() {

 abstract val dao:UserDao
    companion object{
        private var instance: TestUserDatabase? = null
        fun getInstance(application : Application): TestUserDatabase {
            return instance ?: Room.inMemoryDatabaseBuilder(
                application.applicationContext ,
                TestUserDatabase::class.java ,

            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }
}