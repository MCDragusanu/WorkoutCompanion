package com.example.workoutcompanion.core.presentation.app_state

import androidx.compose.runtime.Stable
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


data class WorkoutCompanionAppState(val userProfile : UserProfile ,
                                    val trainingParameters : TrainingParameters ,
                                    val weightUnitOfMeasurement: UnitOfMeasurement ,
                                    val heightUnitOfMeasurement: UnitOfMeasurement ,
                                    val currentLanguage: Language,

)

