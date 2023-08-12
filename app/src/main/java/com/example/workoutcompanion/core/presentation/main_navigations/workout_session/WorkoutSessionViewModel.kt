package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Testing


import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutSessionViewModel @Inject constructor(private val workoutRepository : WorkoutRepository ,
                                                  @Testing private val profileRepository : ProfileRepository,
                                                  @Testing private val appStateManager : AppStateManager
                                                  ):ViewModel() {

    private var _sessionUid : Long? = null

    private var _userUid : String? = null

    private var appState : WorkoutCompanionAppState? = null

    private val _session = MutableStateFlow<WorkoutSession?>(null)
    val session = _session.asStateFlow()

    private val _slots = MutableStateFlow<List<ExerciseSlot>>(emptyList())
    val exerciseSlots = _slots.asStateFlow()


    private val _sets = MutableStateFlow<List<SetSlot>>(emptyList())
    val sets = _sets.asStateFlow()

    private val _currentSet = MutableStateFlow(_sets.value.firstOrNull()?.uid ?: -1)
    val currentSet = _currentSet.asStateFlow()
    fun retrieveProfile(uid : String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            appState = appStateManager.getAppState(uid)
        }
    }

    fun retrieveSession(sessionUid : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Test" , "Received session uid = $sessionUid")
            _sessionUid = sessionUid
            workoutRepository.getSession(sessionUid).onFailure {
                it.printStackTrace()
            }.onSuccess {

                it?.let { newSession ->
                    _session.update { newSession }
                    val slotList = WorkoutSession.parseUids(newSession.slotList)
                    val setList = WorkoutSession.parseUids(newSession.setList)

                    Log.d("Test" , "slot count = ${slotList.size}")
                    Log.d("Test" , "set count = ${setList.size}")
                    _slots.update {
                        slotList.map { workoutRepository.getExerciseSlotByUid(it).getOrNull()!! }
                    }
                    _sets.update {
                        setList.map {
                            workoutRepository.getSetByUid(it.toInt()).getOrNull()!!
                        }
                    }
                    _currentSet.update { _sets.value[session.value?.cursorPosition ?: 0].uid }
                    Log.d("Test" , " Set Count = ${_sets.value.size}")
                }
            }
        }
    }

    fun onNextItem() {
        //TODO think of a way to store the sets to make sure there are in order, most probably a queue for each exercise slot

        val currentCursor =
            if (_currentSet.value + 1 in _sets.value.indices) currentSet.value + 1 else currentSet.value
        _currentSet.update { currentCursor }
        viewModelScope.launch {
            _session.value?.let {
                Log.d("Test" , "Update called")
                workoutRepository.updateSession(session = it.copy(cursorPosition = currentCursor))
                    .onFailure {
                        it.printStackTrace()
                    }
            }
        }
    }
}