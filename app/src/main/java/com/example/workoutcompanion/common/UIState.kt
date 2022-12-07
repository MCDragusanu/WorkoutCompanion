package com.example.workoutcompanion.common

enum class UIState {
    Disabled,
    Enabled,
    Pressed,
    Completed,
    Loading,
    Error;
    fun isError() = this == Error
}