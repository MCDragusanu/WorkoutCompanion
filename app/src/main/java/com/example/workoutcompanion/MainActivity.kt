package com.example.workoutcompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.login.screen.LoginViewModel
import com.example.workoutcompanion.navigation.MainNavigation
import com.example.workoutcompanion.on_board.view_model.OnBoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {


            WorkoutCompanionTheme {// A surface container using the 'background' color from the theme
                val viewModel = hiltViewModel<LoginViewModel>()
                val onBoardViewModel = hiltViewModel<OnBoardViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(
                        loginViewModel = viewModel,
                        onBoardViewModel = onBoardViewModel,
                        onLoginCompleted = { Log.d("Test", "Login Successfully with uid = $it") },
                        onSignUpCompleted = {

                        }
                    )
                }
            }
        }
    }
}

