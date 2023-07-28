package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

sealed class Field(val content:String){
    class Reps(content : String ):Field(content )
    class Weight(content : String ):Field(content )
}
