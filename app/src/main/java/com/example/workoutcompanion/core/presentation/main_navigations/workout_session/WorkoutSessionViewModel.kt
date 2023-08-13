package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.di.Testing


import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.google.common.collect.Sets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
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

    private val _exerciseSlots = MutableStateFlow<List<ExerciseSlot>>(emptyList())
    val exerciseSlots = _exerciseSlots.asStateFlow()

    private val _setSlots = MutableStateFlow(emptyList<SetSlot>())
    val setSlots = _setSlots.asStateFlow()

    private val setQueueBuffer : MutableList<Int> = mutableListOf()

    private val undoSetStack : Stack<Int> = Stack()

    private val _currentSet = MutableStateFlow<SetSlot?>(null)
    val currentSet = _currentSet.asStateFlow()

    fun retrieveProfile(uid : String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            appState = appStateManager.getAppState(uid)
        }
    }

    fun retrieveSession(sessionUid : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSession =
                workoutRepository.getSession(sessionUid).onFailure { it.printStackTrace() }
                    .getOrNull()
            newSession?.let { session ->
                _session.update { session }
                val slotUids = WorkoutSession.parseUids(session.slotList)
                val setUids = WorkoutSession.parseUids(session.setList)


                slotUids.onEach {
                    val newSlot = workoutRepository.getExerciseSlotByUid(it).onFailure { it.printStackTrace() }.getOrNull()
                    newSlot?.let { slot->
                        _exerciseSlots.update { it+slot }
                    }
                }

                setUids.onEach {
                    val newSet =
                        workoutRepository.getSetByUid(it.toInt()).onFailure { it.printStackTrace() }
                            .getOrNull()
                    newSet?.let { set ->
                        _setSlots.update { it + set }
                        if (set.uid == session.cursorPosition) {
                            _currentSet.update { set }
                        } else if (SetSlot.SetStatus.values()[set.status].isDefault()) {
                            setQueueBuffer.add(set.uid)
                        } else undoSetStack.add(set.uid)
                    }
                }
            }
        }
    }




    fun onNextItem() {

        Log.d("Test" , "Current Undo Stack = $undoSetStack Current Queue = $setQueueBuffer")

        val currentEntry = setQueueBuffer.removeFirstOrNull()

        if (currentEntry == null) {
            Log.d("Test" , "Queue is Empty")
            return
        }
        undoSetStack.push(currentEntry)

        val currentSetState = _setSlots.value.firstOrNull { it.uid == currentEntry }

        if (currentSetState == null) {
            Log.d("Test" , "Set with uid = $currentEntry not found")
            return
        }


        _currentSet.update { currentSetState }

        viewModelScope.launch(Dispatchers.IO) {
            _session.value?.let {
                //  Log.d("Test" , "Update block called")
                workoutRepository.updateSession(it.copy(cursorPosition = currentSetState.uid))
                    .onFailure {
                        it.printStackTrace()
                    }.onSuccess {
                        Log.d("Test" , "Cursor = ${_currentSet.value!!.uid}")
                    }
                workoutRepository.updateSet(currentSetState.copy(status = SetSlot.SetStatus.Completed.ordinal).apply { this.uid = currentSetState.uid })



                _setSlots.update {
                    it.replace(currentSetState.copy(status = SetSlot.SetStatus.Completed.ordinal)) {
                        it.uid == currentSetState.uid
                    }
                }
            }
        }
    }

    fun onPrevItem() {

        //TODO when you press next and the back the cursor won't change
        //TODO because you take and put it in the queue or stack , you don't exclude it from both
        //TODO think of a way to handle this
        //TODO add a way to track the progress , like which sets were completed or failed and display that accordingly
        //TODO implement a session record class that holds the set uid and a status that can be -1 -> failed ; 0 ->not visited ; 1 -> completed;

        if (undoSetStack.isNotEmpty()) {

            Log.d("Test" , "Current Undo Stack = $undoSetStack Current Queue = $setQueueBuffer")
            val currentSetEntry = undoSetStack.pop()

            if (currentSetEntry == null) {
                Log.d("Test" , "Stack is Empty")
                return
            }

            setQueueBuffer.add(0 , currentSetEntry)


            Log.d("Queue" , " Stack = ${undoSetStack}, New Queue = $setQueueBuffer")

            val currentSet =
                _setSlots.value.firstOrNull { it.uid == currentSetEntry }

            if (currentSet == null) {
                Log.d("Test" , "Set with uid = $currentSetEntry not found")
                return
            }

            _currentSet.update { currentSet }

            viewModelScope.launch(Dispatchers.IO) {
                _session.value?.let {
                    workoutRepository.updateSession(it.copy(cursorPosition = currentSet.uid))
                        .onFailure {
                            it.printStackTrace()
                        }.onSuccess {
                            Log.d("Test" , "Cursor = ${_currentSet.value!!.uid}")
                        }
                    workoutRepository.updateSet(currentSet.copy(status = SetSlot.SetStatus.Default.ordinal).apply { this.uid = currentSet.uid })

                    _setSlots.update {
                        it.replace(currentSet.copy(status = SetSlot.SetStatus.Completed.ordinal)) {
                            it.uid == currentSet.uid
                        }
                    }
                }
            }
        }
    }
}




