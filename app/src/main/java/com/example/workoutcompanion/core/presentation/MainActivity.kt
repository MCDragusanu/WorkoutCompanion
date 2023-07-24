package com.example.workoutcompanion.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
       super.onCreate(savedInstanceState)
        setContent {
           WorkoutCompanionTheme {
               MainNavigation.MainNavigation()
           }
       }
    }
}