package com.example.workoutcompanion.presentation.graphs.entry.screens.on_board

import androidx.lifecycle.ViewModel
import com.example.workoutcompanion.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnBoardViewModel:ViewModel() {


   val animationFlow: Flow<AppFeature> = flow {
        val feature1 = AppFeature(R.drawable.icon_evolution, R.string.ProgressiveOverloadAssistant, R.string.Body_ProgressiveOverloadAssistant)
        val feature2 = AppFeature(R.drawable.icon_chart, R.string.WorkoutLogger, R.string.Body_WorkoutLogger)
        val feature3 = AppFeature(R.drawable.icon_customization, R.string.WorkoutBuilder, R.string.Body_WorkoutBuilder)
        val feature4 = AppFeature(R.drawable.icon_science, R.string.BackedByScience, R.string.Body_BackedByScience)

        delay(1000)
        emit(feature1)
        delay(250)
        emit(feature2)
        delay(250)
        emit(feature3)
        delay(250)
        emit(feature4)

    }
}