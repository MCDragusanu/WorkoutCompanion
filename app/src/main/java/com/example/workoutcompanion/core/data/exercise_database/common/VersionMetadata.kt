package com.example.workoutcompanion.core.data.exercise_database.version_control.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "database_version_table")
class VersionMetadata(@PrimaryKey(autoGenerate = true)
                      val uid:Int = 0 ,
                      val versionNumber:String = "1_0_ALPHA" ,
                      val dateOfPublish:Long = 1689673572836 ,
                      val size:Int = 84 ,
                      val isDeprecated:Boolean = false ,
                      )