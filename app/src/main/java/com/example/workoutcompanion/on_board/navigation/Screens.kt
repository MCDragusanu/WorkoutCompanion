package com.example.workoutcompanion.on_board.navigation

sealed class Screens(val route:String) {
    object LoginScreen: Screens("Login_Screen")
    object FeatureScreen: Screens("Feature_Screen")
    object AccountScreen: Screens("Account_Screen")
    object AccountCompletedScreen: Screens("Account_Completed_Screen")

}