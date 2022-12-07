package com.example.workoutcompanion.common

import java.time.LocalTime


data class Time(val hour :Int , val minute:Int , val second:Int, val format24h : Boolean = true) {
    companion object {
        fun fromLocalTime(localTime: LocalTime) =
            Time(hour = localTime.hour, minute = localTime.minute, second = localTime.second)
    }
}