package com.example.workoutcompanion.navigation

sealed class Screens(val route:String) {
    object LoginScreen: Screens("Login_Screen")

}