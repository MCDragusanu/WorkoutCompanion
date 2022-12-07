package com.example.workoutcompanion.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        AccountInformation::class],
    exportSchema = false,
    version = 2
)
abstract class AccountDatabase : RoomDatabase() {
    abstract val accountDao: AccountDao

    companion object {
        @Volatile
        private var instance: AccountDatabase? = null
        fun getInstance(context: Context): AccountDatabase {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AccountDatabase::class.java,
                    "account_database"
                ).fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            }
        }
    }
}