package com.example.workoutcompanion.common.composables

import com.example.workoutcompanion.common.composables.UIState

data class FormState(val text:String = "", val errorStringResource:Int? = null, val state: UIState = UIState.Enabled)