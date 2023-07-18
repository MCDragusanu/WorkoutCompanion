package com.example.workoutcompanion.common

sealed class Resource<T>(val data:T? , val exception:Exception?) {
    class Loading<T>:Resource<T>(null , null)
    class Error<T>(exception: Exception):Resource<T>(null , exception)
    class Completed<T>(result:T):Resource<T>(result , null)
}