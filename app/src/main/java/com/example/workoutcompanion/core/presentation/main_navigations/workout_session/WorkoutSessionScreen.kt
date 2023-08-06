package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation

object WorkoutSessionScreen:MainNavigation.Screens("Workout_Session_Screen") {
    @Composable
    operator fun invoke(viewModel : WorkoutSessionViewModel) {
        val session by viewModel.session.collectAsState()
        session?.let {

            Box(modifier = Modifier.fillMaxSize()){

                Text(
                    text = it.content.replace("{" , "\n").replace("}" , "\n").replace("-" , "\t")
                        .replace(":" , " "),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}