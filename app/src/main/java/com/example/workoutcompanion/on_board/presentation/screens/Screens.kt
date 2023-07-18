package com.example.workoutcompanion.on_board.presentation.screens

sealed class Screens(val route:String) {
    object FeatureScreen: Screens("Feature_Screen")
    object AccountScreen: Screens("Account_Screen")
    object AccountCompletedScreen: Screens("Account_Completed_Screen")
    object ProfileScreen: Screens("Profile_Screen")

}