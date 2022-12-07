package com.example.workoutcompanion.graphs

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutcompanion.common.Height
import com.example.workoutcompanion.common.Weight
import com.example.workoutcompanion.room.AccountRepository
import com.example.workoutcompanion.screens.entry.height_screen.HeightScreen
import com.example.workoutcompanion.screens.entry.create_account_account.CreateAccount
import com.example.workoutcompanion.screens.entry.create_account_account.CreateAccountViewModel
import com.example.workoutcompanion.screens.entry.on_board_screen.OnboardScreen
import com.example.workoutcompanion.screens.entry.weight_screen.WeightsScreen
import com.example.workoutcompanion.screens.entry.welcome_screen.WelcomeScreen
import com.example.workoutcompanion.screens.entry.welcome_screen.WelcomeScreenViewModel

object EntryGraph {
    @Composable
    operator fun invoke(accountRepository: AccountRepository,
                        createAccountViewModel: CreateAccountViewModel,
                        entryGraphViewModel: EntryGraphViewModel,
                        ) {

        val navController = rememberNavController()
        var accountCreated by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }

        NavHost(
            navController = navController,
            startDestination = Destinations.WelcomeScreen.route
        ) {
            composable(Destinations.WelcomeScreen.route) {
                WelcomeScreen.Main(viewModel = WelcomeScreenViewModel(), onLogin = {

                }, onSignUp = {
                    navController.navigate(Destinations.OnBoardScreen.route)
                })
            }
            composable(Destinations.OnBoardScreen.route) {
                OnboardScreen.Main(onLogin = {
                    navController.navigate(Destinations.WelcomeScreen.route)
                    navController.popBackStack()
                }, onCreateAccount = {
                    navController.navigate(Destinations.CreateAccountScreen.route)

                })
            }
            composable(Destinations.CreateAccountScreen.route) {
                CreateAccount.Main(
                    onSubmitResult = {
                        Log.d("Test", "Result received from Create Account Screen")
                        accountCreated = true
                        email=it.accountInformation?.email?:"Default email"
                        entryGraphViewModel.setState(it)
                        navController.navigate(Destinations.WeightScreen.route)
                    },
                    viewModel = createAccountViewModel,
                    isCompleted = accountCreated,
                    onNextScreen = {
                        navController.navigate(Destinations.WeightScreen.route)
                    }
                )
            }
            composable(Destinations.WeightScreen.route) {
                WeightsScreen(
                    onSubmitWeight = { weight, index ->
                        Log.d(
                            "Test",
                            "choice = ${if (index == 0) "Kgs" else "Lbs"}  value in kgs = ${weight.toKgs()} , value in pounds = ${weight.toPounds()} "
                        )
                        entryGraphViewModel.setWeight(weight, index)
                        navController.navigate(Destinations.HeightScreen.route)
                    },
                    startWeight = Weight(
                        entryGraphViewModel.onBoardState.accountInformation?.weightInKgs ?: 75f
                    )
                )
            }
            composable(Destinations.HeightScreen.route) {
                HeightScreen(onGetHeight = { height, index ->
                    Log.d(
                        "Test",
                        "choice = ${if (index == 0) "Kgs" else "Lbs"}  value in kgs = ${height.toCms()} , value in Feet Inches =  ${height.asPoundsString()} "
                    )
                    entryGraphViewModel.setHeight(
                        Height(
                            entryGraphViewModel.onBoardState.accountInformation?.heightInCms ?: 175f
                        ), entryGraphViewModel.onBoardState.accountInformation?.unitOfMeasureHeight ?: 0
                    )
                    navController.navigate(Destinations.TrainingInfoScreen.route)
                }, isDarkTheme = true)
            }
            composable(Destinations.TrainingInfoScreen.route) {

            }
        }
    }

    sealed class Destinations(val route : String){
        object WelcomeScreen:Destinations("welcome_screen")
        object OnBoardScreen:Destinations(route = "on_board_screen")
        object CreateAccountScreen:Destinations(route = "create_account_screen")
        object WeightScreen : Destinations(route = "weight_screen")
        object HeightScreen : Destinations(route = "height_screen")
        object TrainingInfoScreen:Destinations(route = "training_info_screen")
    }


}