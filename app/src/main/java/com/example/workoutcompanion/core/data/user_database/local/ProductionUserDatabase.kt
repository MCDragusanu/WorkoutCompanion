package com.example.workoutcompanion.core.data.user_database.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
@Database(entities = [UserProfile::class] , version = 2)
abstract class ProductionUserDatabase: RoomDatabase(){

abstract val dao:UserDao
 companion object{
     private var instance: ProductionUserDatabase? = null
     fun getInstance(application : Application): ProductionUserDatabase {
         return instance ?: Room.databaseBuilder(
             application.applicationContext ,
             ProductionUserDatabase::class.java ,
             "user_database"
         ).fallbackToDestructiveMigration().build().apply {
             instance = this
         }
     }
 }
}