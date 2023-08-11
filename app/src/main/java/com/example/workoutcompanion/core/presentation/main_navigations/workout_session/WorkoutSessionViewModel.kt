package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Testing


import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutSessionViewModel @Inject constructor(private val workoutRepository : WorkoutRepository ,@Testing private val profileRepository : ProfileRepository):ViewModel() {

    private var _sessionUid : Long? = null

    private var _userUid : String? = null

    private var _profile : UserProfile? = null

    private val _session = MutableStateFlow<WorkoutSession?>(null)
    val session = _session.asStateFlow()

    fun retriveProfile(uid : String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getProfileFromLocalSource(uid).onSuccess {

                _profile = it
            }.onFailure {
                Log.d("Test" , it.stackTraceToString())
            }
        }
    }

    fun retrieveSession(sessionUid : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Test" , "Received session uid = $sessionUid")
            _sessionUid = sessionUid
            workoutRepository.getSession(sessionUid).onFailure {
                it.printStackTrace()
            }.onSuccess {
                withContext(Dispatchers.Main) {

                    _session.emit(it)
                }
            }
        }
    }
}