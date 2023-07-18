package com.example.workoutcompanion.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.workoutcompanion.login.screen.LoginScreen
import com.example.workoutcompanion.login.screen.LoginViewModel
import com.example.workoutcompanion.on_board.presentation.screens.Screens.FeatureScreen
import com.example.workoutcompanion.on_board.presentation.screens.account.AccountScreen
import com.example.workoutcompanion.on_board.presentation.screens.account.RegistrationCompletedScreen
import com.example.workoutcompanion.on_board.presentation.screens.feature.FeatureScreen
import com.example.workoutcompanion.on_board.view_model.OnBoardViewModel
import com.example.workoutcompanion.on_board.presentation.screens.Screens.AccountScreen as RegistrationScreen

@Composable
fun MainNavigation(loginViewModel: LoginViewModel, onBoardViewModel : OnBoardViewModel, onLoginCompleted:(String)->Unit, onSignUpCompleted:(String)->Unit, ) {


    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route,
        enterTransition = { slideInHorizontally { -it } },
        exitTransition = { slideOutHorizontally { -it } }) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(viewModel = loginViewModel, onSignUp = {
                navController.navigate(FeatureScreen.route)
            }, onLogin = onLoginCompleted)
        }
        navigation(
            route = "on_bord",
            startDestination = FeatureScreen.route
        ) {
            composable(FeatureScreen.route) {
                FeatureScreen(onBackIsPressed = { }, onSighUp = {
                    navController.navigate(RegistrationScreen.route)
                })
            }
            composable(RegistrationScreen.route) {
                onBoardViewModel.setSignUpCallback {
                    navController.popBackStack(FeatureScreen.route , true)
                    navController.navigate(com.example.workoutcompanion.on_board.presentation.screens.Screens.AccountCompletedScreen.route)
                }
                AccountScreen(
                    emailFormState = onBoardViewModel.emailFormState,
                    passwordFormState = onBoardViewModel.passwordFormState,
                    termsIsChecked = onBoardViewModel.termsIsChecked,
                    termState = onBoardViewModel.termsState,
                    ctaState = onBoardViewModel.ctaState,
                    errorChannel = onBoardViewModel.errorChannel,
                    passwordProperties = onBoardViewModel.passwordProperties,
                    onEmailChanged = {onBoardViewModel.onEmailChanged(it)},
                    onPasswordChanged = {onBoardViewModel.onPasswordChanged(it)},
                    onTermsChanged = {onBoardViewModel.onTermsChanged(it)},
                    onBackIsPressed = { /*TODO*/ },
                    onSignUp = {onBoardViewModel.onSignUp()}
                    )
            }
            composable(com.example.workoutcompanion.on_board.presentation.screens.Screens.AccountCompletedScreen.route ){
                val email = onBoardViewModel.getUserEmail()
                RegistrationCompletedScreen(onContinue = {
                           onSignUpCompleted(onBoardViewModel.getUserUid())
                }, email = email)
            }
        }
    }
}
