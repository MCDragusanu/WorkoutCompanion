package com.example.workoutcompanion.screens.entry.on_board_screen

import androidx.lifecycle.ViewModel
import com.example.workoutcompanion.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardViewModel:ViewModel() {

     data class AppFeature(val imageId:Int , val headLineId:Int , val body:Int)


      suspend fun onStart(){
          val feature1 = AppFeature(R.drawable.icon_evolution, R.string.ProgressiveOverloadAssistant, R.string.Body_ProgressiveOverloadAssistant)
          val feature2 = AppFeature(R.drawable.icon_chart, R.string.WorkoutLogger, R.string.Body_WorkoutLogger)
          val feature3 = AppFeature(R.drawable.icon_customization, R.string.WorkoutBuilder, R.string.Body_WorkoutBuilder)
          val feature4 = AppFeature(R.drawable.icon_science, R.string.BackedByScience, R.string.Body_BackedByScience)
          _showButtonFlow.emit(false)
          delay(1000)
          _featureFlow.emit(feature1)
          delay(250)
          _featureFlow.emit(feature2)
          delay(250)
          _featureFlow.emit(feature3)
          delay(250)
          _featureFlow.emit(feature4)
          _showButtonFlow.emit(true)
          featureFlow = _featureFlow.asStateFlow()
          showButton = _showButtonFlow.asStateFlow()

     }
     private val _featureFlow = MutableStateFlow<AppFeature>(AppFeature(imageId = -1 , headLineId = -1 , body = -1))
     var featureFlow = _featureFlow.asStateFlow()
    private val _showButtonFlow = MutableStateFlow(false)
     var showButton = _showButtonFlow.asStateFlow()
}