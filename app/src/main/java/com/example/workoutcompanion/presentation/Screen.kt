package com.example.workoutcompanion.presentation

sealed class Screen(val name :String){

    object WelcomeScreen : Screen("welcome_screen")
}
