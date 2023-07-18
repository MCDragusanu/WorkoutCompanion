package com.example.workoutcompanion.common

enum class Result {
    Error,
    Completed;
    fun isCompleted() = this == Completed
    fun isError() = this == Error
}