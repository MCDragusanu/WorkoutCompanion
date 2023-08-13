package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Testing


import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot.Companion.WarmUp
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot.Companion.WorkingSet
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

    private val setQueueBuffer : MutableList<Int> = mutableListOf<Int>()

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
        Log.d("Bug" , "Retrieve Session called once")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Test" , "Received session uid = $sessionUid")
            _sessionUid = sessionUid
            workoutRepository.getSession(sessionUid).onFailure {
                it.printStackTrace()
            }.getOrNull()?.let { session ->
                _session.update { session }
                val slotsUid = WorkoutSession.parseUids(session.slotList)
                val setsUid = WorkoutSession.parseUids(session.setList)
                Log.d("Test" , session.toString())
                val retrievedSlots = async {
                    slotsUid.map { uid ->
                        workoutRepository.getExerciseSlotByUid(uid).onFailure {
                            it.printStackTrace()
                        }.getOrNull()!!
                    }
                }
                val retrievedSets = async {
                    setsUid.map { uid ->
                        workoutRepository.getSetByUid(uid.toInt()).onFailure {
                            it.printStackTrace()
                        }.onSuccess { set ->
                            if (set.uid == session.cursorPosition) {
                                _currentSet.update { set }
                                Log.d("Test" , "cursor has changed")
                            }
                        }.getOrNull()!!
                    }
                }
                retrievedSlots.await().onEach { slot ->
                    val sets =
                        retrievedSets.await().filter { set -> set.exerciseSlotUid == slot.uid }
                    val warmUps = sets.filter { it.type == WarmUp }.sortedBy { it.index }
                    val workingSets = sets.filter { it.type == WorkingSet }.sortedBy { it.index }

                    setQueueBuffer.addAll((warmUps + workingSets).sortedBy { it.index }
                        .map { it.uid })

                    _exerciseSlots.update { it + slot }
                    _setSlots.update { it + warmUps + workingSets }
                }

                initCursor(session)

                Log.d(
                    "Test" ,
                    "Queue = ${setQueueBuffer} , Stack = ${undoSetStack}, cursor = ${session.cursorPosition}"
                )
            }
        }
    }

    fun initCursor(session : WorkoutSession) {

        var first = setQueueBuffer.removeFirstOrNull()

        first?.let {
            undoSetStack.push(it)
            while (first != null && first != session.cursorPosition) {
                undoSetStack.push(first)
                first = setQueueBuffer.removeFirstOrNull()
            }
            Log.d(
                "Test" ,
                "Queue =$setQueueBuffer , Stack = $undoSetStack , cursor = ${session.cursorPosition}"
            )
        }
    }

    fun onNextItem() {

        Log.d("Test" , "Current Undo Stack = $undoSetStack Current Queue = $setQueueBuffer")

        val currentSetUid = setQueueBuffer.removeFirstOrNull()

        if (currentSetUid == null) {
            Log.d("Test" , "Queue is Empty")
            return
        }
        undoSetStack.push(currentSetUid)

        val currentSet = _setSlots.value.firstOrNull { it.uid == currentSetUid }

        if (currentSet == null) {
            Log.d("Test" , "Set with uid = $currentSetUid not found")
            return
        }


        _currentSet.update { currentSet }

        viewModelScope.launch(Dispatchers.IO) {
            _session.value?.let {
              //  Log.d("Test" , "Update block called")
                workoutRepository.updateSession(it.copy(cursorPosition = currentSet.uid))
                    .onFailure {
                        it.printStackTrace()
                    }.onSuccess {
                        Log.d("Test" , "Cursor = ${_currentSet.value!!.uid}")
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
            val currentSetUid = undoSetStack.pop()

            if (currentSetUid == null) {
                Log.d("Test" , "Stack is Empty")
                return
            }

            setQueueBuffer.add(0 , currentSetUid)


            Log.d("Queue" , " Stack = ${undoSetStack}, New Queue = $setQueueBuffer")

            val currentSet = _setSlots.value.firstOrNull { it.uid == currentSetUid }

            if (currentSet == null) {
                Log.d("Test" , "Set with uid = $currentSetUid not found")
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
                }
            }
        }
    }
}




