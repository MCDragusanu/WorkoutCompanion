package com.example.workoutcompanion.common.composables

enum class UIState {
    Enabled,
    Loading,
    Completed,
    Error,
    Disbled;
    fun isError() = this == Error
    fun isCompleted() = this == Completed
    fun isLoading() = this == Loading
    fun isEnabled() = this == Enabled
    fun isDisabled() = this == Disbled
}