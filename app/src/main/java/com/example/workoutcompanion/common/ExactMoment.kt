package com.example.workoutcompanion.common

data class ExactMoment(val day:Int ,
                       val month: Int,
                       val _year:Int,
                       val hour:Int,
                       val minute:Int,
                       val second:Int,
                       val in24hFormat:Boolean = true
                       ) {
    fun asString() = buildString {
        append(day)
        append("/")
        append(month)
        append("/")
        append(_year)
        append("/")
        append(minute)
        append("/")
        append(second)
        append("/")
        append(in24hFormat)
    }
    companion object {
        fun fromDate(date: Date = Date.fromLocalDate()) =
            ExactMoment(
                day = date.dayOfMonth,
                month = date.monthOfYear,
            _year = date.year,
                hour = 12,
                minute = 0,
                second = 0
            )
    }
}