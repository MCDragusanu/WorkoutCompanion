package com.example.workoutcompanion.presentation.graphs

sealed class Screen(val name :String){

    object WelcomeScreen : Screen("welcome_screen")
    object OnBoardScreen : Screen("on_board_screen")
}
