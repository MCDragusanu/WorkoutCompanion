package com.example.workoutcompanion.presentation.graphs.entry

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutcompanion.presentation.graphs.Screen
import com.example.workoutcompanion.presentation.graphs.entry.screens.on_board.OnboardScreen
import com.example.workoutcompanion.presentation.graphs.entry.screens.welcome.WelcomeScreen
import com.example.workoutcompanion.presentation.graphs.entry.screens.welcome.WelcomeScreenViewModel

object EntryGraph {

    @Composable
    fun EntryGraph() {
        val navController = rememberNavController()
        val viewModel = WelcomeScreenViewModel()
        NavHost(navController = navController, startDestination = Screen.WelcomeScreen.name) {
            composable(Screen.WelcomeScreen.name) {
                WelcomeScreen.Main(viewModel = viewModel, onCreateAccount = {
                    navController.navigate(Screen.OnBoardScreen.name)
                })
            }
            composable(Screen.OnBoardScreen.name) {
                OnboardScreen.Main(onLogin = {
                    navController.navigate(Screen.WelcomeScreen.name)
                }, onCreateAccount = {
                    //nav to credentials
                })
            }
        }
    }
}