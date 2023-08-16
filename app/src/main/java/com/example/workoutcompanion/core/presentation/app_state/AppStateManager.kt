package com.example.workoutcompanion.core.presentation.app_state

import android.util.Log
import androidx.compose.ui.text.intl.Locale

import com.example.workoutcompanion.core.data.di.Testing

import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.domain.use_cases.RetrieveTrainingParameters
import com.example.workoutcompanion.core.domain.use_cases.TranslateLanguageCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppStateManager @Inject constructor(private val workoutRepository : WorkoutRepository ,
                                          @Testing
                                          private val profileRepository : ProfileRepository ,) {


    suspend fun getAppState(userUid : String) : WorkoutCompanionAppState? {

        var current : WorkoutCompanionAppState?

        val profile =
            profileRepository.getProfileFromLocalSource(userUid).getOrNull() ?: guestProfile
        val trainingParameters =
            workoutRepository.getTrainingParameters(userUid).onFailure { it.printStackTrace() }
                .getOrNull()
                ?: workoutRepository.createInitialParameters(userUid).onFailure {
                }.getOrNull()
                ?: RetrieveTrainingParameters().getDefaultParameters(userUid)
        val weight = UnitOfMeasurement.Kilograms
        val height = UnitOfMeasurement.Centimeters
        val language = TranslateLanguageCode().execute(Locale.current.language)

        current = WorkoutCompanionAppState(
            userProfile = profile ,
            trainingParameters = trainingParameters ,
            weightUnitOfMeasurement = weight ,
            heightUnitOfMeasurement = height ,
            currentLanguage = language
        )

        return current
    }
}