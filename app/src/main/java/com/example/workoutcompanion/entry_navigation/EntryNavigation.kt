package com.example.workoutcompanion.entry_navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.workoutcompanion.entry_navigation.login.screen.LoginScreen
import com.example.workoutcompanion.entry_navigation.login.viewmodel.LoginViewModel

import com.example.workoutcompanion.entry_navigation.on_board.screens.account.AccountScreen
import com.example.workoutcompanion.on_board.presentation.account.RegistrationCompletedScreen
import com.example.workoutcompanion.entry_navigation.on_board.screens.feature.FeatureScreen
import com.example.workoutcompanion.entry_navigation.on_board.view_model.OnBoardViewModel


@Composable
fun EntryNavigation(
                   onLoginCompleted:(String)->Unit ,
                   startWorkoutDesignActivity:(String)->Unit ,
                   startMainActivity:(String)->Unit ,
                   ) {


    val navController = rememberNavController()
    val loginViewModel = hiltViewModel<LoginViewModel>().apply {
        this.setLoginCallback {
            startMainActivity(it)
        }
    }
    val onBoardViewModel = hiltViewModel<OnBoardViewModel>().apply {
        this.setNavigationCallback {
            navController.popBackStack(Screens.FeatureScreen.route , true)
            navController.navigate(Screens.AccountCompletedScreen.route)
        }
    }
    NavHost(
        navController = navController ,
        startDestination = Screens.LoginScreen.route ,
        enterTransition = { slideInHorizontally { -it } } ,
        exitTransition = { slideOutHorizontally { -it } }) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(viewModel = loginViewModel , onSignUp = {
                navController.navigate(Screens.FeatureScreen.route)
            } , onLogin = onLoginCompleted)
        }
        navigation(
            route = "on_bord" ,
            startDestination = Screens.FeatureScreen.route
        ) {
            composable(Screens.FeatureScreen.route) {
                FeatureScreen(onBackIsPressed = { } , onSighUp = {
                    navController.navigate(Screens.AccountScreen.route)
                })
            }
            composable(Screens.AccountScreen.route) {

                AccountScreen(
                    emailFormState = onBoardViewModel.emailFormState ,
                    passwordFormState = onBoardViewModel.passwordFormState ,
                    termsIsChecked = onBoardViewModel.termsIsChecked ,
                    termState = onBoardViewModel.termsState ,
                    ctaState = onBoardViewModel.ctaState ,
                    errorChannel = onBoardViewModel.errorChannel ,
                    firstNameFormState = onBoardViewModel.firstNameFormState ,
                    lastNameFormState = onBoardViewModel.lastNameFormState ,
                    passwordProperties = onBoardViewModel.passwordProperties ,
                    onEmailChanged = { onBoardViewModel.onEmailChanged(it) } ,
                    onPasswordChanged = { onBoardViewModel.onPasswordChanged(it) } ,
                    onFirstNameChanged = { onBoardViewModel.onFirstNameChanged(it) } ,
                    onLastNameChanged = { onBoardViewModel.onLastNameChanged(it) } ,
                    onTermsChanged = { onBoardViewModel.onTermsChanged(it) } ,
                    onBackIsPressed = {} ,
                    onSignUp = { onBoardViewModel.onSignUp() }
                )
            }
            composable(Screens.AccountCompletedScreen.route) {
                RegistrationCompletedScreen(onCreateWorkout = {
                    startWorkoutDesignActivity(onBoardViewModel.getUserUid())
                } , onSkip = {
                    startMainActivity(onBoardViewModel.getUserUid())
                })
            }
        }
    }
}
