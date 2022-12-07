package com.example.workoutcompanion.common

import android.util.Log
import java.time.LocalDate

data class Date(
                val dayOfMonth:Int = 1,
                val monthOfYear :Int = 1,
                val year:Int = 2023
                ){
   companion object{
       fun fromLocalDate(localDate: LocalDate = LocalDate.now()) = Date(localDate.dayOfMonth , localDate.monthValue , localDate.year)
       fun buildFromString(dateOfBirth: String): Date {
           val content = dateOfBirth.split("/")
           return Date(dayOfMonth = content[0].toInt() , monthOfYear = content[1].toInt() , year = content[2].toInt())
       }
   }
   fun asString() = buildString {
       append(dayOfMonth)
       append("/")
       append(monthOfYear)
       append("/")
       append(year)
   }
}
