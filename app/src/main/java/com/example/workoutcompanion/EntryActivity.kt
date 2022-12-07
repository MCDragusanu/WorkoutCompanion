package com.example.workoutcompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutcompanion.presentation.CallToActionEvent
import com.example.workoutcompanion.presentation.WelcomeScreen
import com.example.workoutcompanion.presentation.WelcomeScreenState
import com.example.workoutcompanion.ui.theme.WorkoutCompanionTheme

class EntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutCompanionTheme {
                var state = WelcomeScreenState(
                    isLoading = false,
                    txtEmail = "",
                    txtPassword = "",
                    isLoggedIn = false,
                    btnLoginState = CallToActionEvent.Neutral,
                    error = null
                )
                // A surface container using the 'background' color from the theme
                WelcomeScreen.Main(
                    onStartLogin = { email, password ->
                        Log.d("Test", "Start login for email = $email , password = $password")
                    },
                    state = state,
                    onStateChanged = {
                        state = it
                        Log.d("Test" , "State updated")
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkoutCompanionTheme {
        Greeting("Android")
    }
}