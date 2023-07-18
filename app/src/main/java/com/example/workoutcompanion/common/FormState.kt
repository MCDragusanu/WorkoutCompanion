package com.example.workoutcompanion.common

data class FormState(val text:String = "", val errorStringResource:Int? = null, val state:UIState = UIState.Enabled)