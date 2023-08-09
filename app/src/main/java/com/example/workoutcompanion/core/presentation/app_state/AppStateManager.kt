package com.example.workoutcompanion.core.presentation.app_state

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import com.example.workoutcompanion.core.data.di.ComponentType

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
                                          @ComponentType(true)
                                          private val profileRepository : ProfileRepository ,) {

    private var _workoutCompanionAppState = MutableStateFlow<WorkoutCompanionAppState?>(null)




    suspend fun getAppState(userUid : String) : StateFlow<WorkoutCompanionAppState?> {
        _workoutCompanionAppState.update {
            var current : WorkoutCompanionAppState?

            val profile =
                profileRepository.getProfileFromLocalSource(userUid).getOrNull() ?: guestProfile
            val trainingParameters =
                workoutRepository.getTrainingParameters(userUid).onFailure {
                    it.printStackTrace()
                    Log.d("Test" , "Failed to retrieve custom parameters")
                }.onSuccess {
                    Log.d("Test" , "Retrieved custom parameters")
                }.getOrNull()
                    ?: workoutRepository.createInitialParameters(userUid).onFailure {
                        Log.d("Test" , "Failed to create default parameters")
                    }.onSuccess {
                        Log.d("Test" , "Default parameters created")
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
            
            current
        }
        return _workoutCompanionAppState.asStateFlow()
    }

    suspend fun updateUserProfile(userProfile : UserProfile) {
        if (_workoutCompanionAppState.value != null) {
            _workoutCompanionAppState.value?.let { currentState ->
                profileRepository.updateLocalProfile(userProfile.uid , userProfile).onFailure {
                    it.printStackTrace()
                    throw it
                }.onSuccess {
                    Log.d("Test" , "Profile has been updated locally")
                }
                profileRepository.updateCloudProfile(userProfile.uid , userProfile).onFailure {
                    it.printStackTrace()
                    throw it
                }.onSuccess {
                    Log.d("Test" , "Profile has been updated on cloud")
                }
                _workoutCompanionAppState.emit(currentState.copy(userProfile = userProfile))
            }
        }
        if (_workoutCompanionAppState.value == null) {
               throw NullPointerException("Cannot update a state that hasn't been created yet")
        }
    }

}