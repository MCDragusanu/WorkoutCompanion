package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.composables.UIState
import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.di.Testing


import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val setQueueBuffer : MutableList<Long> = mutableListOf()

    private val _currentSet = MutableStateFlow<SetSlot?>(null)
    val currentSet = _currentSet.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()


    val parentExercise = _currentSet.map { set ->
        _exerciseSlots.value.find {
            it.uid == (set?.exerciseSlotUid ?: -1)
        }?.exerciseName ?: "Default"
    }
    private val _workoutIsCompleted = MutableStateFlow(false)
    val workoutIsCompleted = _workoutIsCompleted.asStateFlow()

    private val _btnSaveWorkoutState = MutableStateFlow(UIState.Enabled)
    val btnSaveWorkoutState = _btnSaveWorkoutState.asStateFlow()

    private val _exerciseReports = MutableStateFlow<List<ExerciseReport>>(emptyList())
    val exerciseReports = _exerciseReports.asStateFlow()

    val nextItem = _currentSet.map { currentSet ->

        if (currentSet == null) {
            Log.d("Test" , "Current Item is null")
            return@map ""
        }

        val nextSet = getNextSet(currentSet)

        if (nextSet == null) {
            //return the next exercise name
            Log.d("Test" , "There are no sets left for this exercise")
            val slot =
                getNextExerciseName(_exerciseSlots.value.first { it.uid == currentSet.exerciseSlotUid })
            if (slot == null) {
                _workoutIsCompleted.emit(true)
                return@map "Workout Complete!"
            } else return@map slot.exerciseName
        }
        Log.d("Test" , "There is a remaining set")
        _workoutIsCompleted.emit(false)
        return@map buildNextItemStringFromSet(nextSet)
    }

    val currentSetCompletedInQueue = _currentSet.map { current ->
        val remainingSets = setQueueBuffer.dropWhile { it != (current?.uid ?: -1) }.size
        remainingSets
    }

    val currentSetCompletedForExercise = _currentSet.map { currentSet ->
        val remainingSets =
            _setSlots.value.filter { it.exerciseSlotUid == (currentSet?.exerciseSlotUid ?: -1) }
                .dropWhile { it.index <= (currentSet?.index ?: -1) }.size
        remainingSets
    }
    val totalSetsForParent = _currentSet.map { currentSet ->
        val sets =
            _setSlots.value.filter {
                it.exerciseSlotUid == (currentSet?.exerciseSlotUid ?: -1)
            }.size
        sets
    }
    val totalSets = flow {
        emit(setQueueBuffer.size)
    }

    private fun getNextExerciseName(current : ExerciseSlot) : ExerciseSlot? {
        val remainingExercises = _exerciseSlots.value.dropWhile { it.index <= current.index }
        return if (remainingExercises.isEmpty()) {
            null
        } else remainingExercises.first()
    }

    private fun getNextSet(currentSet : SetSlot) : SetSlot? {
        val setList = _setSlots.value.filter { it.exerciseSlotUid == currentSet.exerciseSlotUid }
            .filter { it.index > currentSet.index }
        //.filter { it.status == SetSlot.SetStatus.Default.ordinal }
        return if (setList.isNotEmpty()) {
            setList.first()
        } else null
    }

    private fun buildNextItemStringFromSet(nextItem : SetSlot) : String {
        return "${if (nextItem.type == 0) "Warm Up Set " else "Working Set"} ${nextItem.reps} reps x ${
            String.format(
                "%.1f" ,
                nextItem.weightInKgs
            )
        } "
    }

    fun retrieveProfile(uid : String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            appState = appStateManager.getAppState(uid)
        }
    }

    fun sessionIsValid(uid : Long) : Boolean {
        return (System.currentTimeMillis() - uid < 8 * 3600 * 1000)
    }

    fun retrieveSession(sessionUid : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            val newSession =
                workoutRepository.getSession(sessionUid).onFailure { it.printStackTrace() }
                    .getOrNull()
            newSession?.let { session ->
                _session.update { session }
                val slotUids = WorkoutSession.parseUids(session.slotList)
                val setUids = WorkoutSession.parseUids(session.setList)

                Log.d("Test" , "Session  = $session")

                slotUids.onEach {
                    Log.d("Test" , "Set uid = ${it}")
                    val newSlot = workoutRepository.getExerciseSlotByUid(it)
                        .onFailure { it.printStackTrace() }.getOrNull()
                    newSlot?.let { slot ->
                        _exerciseSlots.update { it + slot }
                        Log.d("Test" , newSlot.exerciseName)
                    }
                }

                setUids.onEach {
                    val newSet =
                        workoutRepository.getSetByUid(it).onFailure { it.printStackTrace() }
                            .getOrNull()
                    newSet?.let { set ->
                        _setSlots.update { it + set }
                        if (set.uid == session.cursorPosition) {
                            _currentSet.update { set }
                        }

                        setQueueBuffer.add(set.uid)
                    }
                }
                Log.d("Test" , setQueueBuffer.toString())
                if (_currentSet.value == null) {
                    _currentSet.update { _setSlots.value.find { setQueueBuffer.first() == it.uid } }
                }
                _isLoading.update { false }
            }
        }
    }


    fun onNextItem() : Int? {
        val currentSetUid = _currentSet.value?.uid
        if (currentSetUid == null) {
            val firstSet = _setSlots.value.firstOrNull() { setQueueBuffer.first() == it.uid }
            _currentSet.update { firstSet }
            return null
        }
        val index = setQueueBuffer.indexOf(currentSetUid)

        val remainingItems = if (index + 1 in setQueueBuffer.indices) setQueueBuffer.subList(
            index + 1 ,
            setQueueBuffer.size
        ) else emptyList()
        if (remainingItems.isEmpty()) {
            Log.d("Test" , "Workout completed")
            return null
        }
        val nextItem = _setSlots.value.firstOrNull { it.uid == remainingItems.first() }
        _currentSet.update { nextItem }

        if (nextItem != null) {
            updateCursorPosition(nextItem)
            return setQueueBuffer.indexOf(nextItem.uid)
        }
        return null

    }

    private fun updateCursorPosition(nextItem : SetSlot) {
        _session.value?.let {
            viewModelScope.launch {
                Log.d("Test" , "Updating cursor position")
                workoutRepository.updateSession(it.copy(cursorPosition = nextItem.uid))
            }
        }
    }

    fun onPrevItem() : Int? {
        val currentSetUid = _currentSet.value?.uid
        if (currentSetUid == null) {
            val firstSet = _setSlots.value.firstOrNull() { setQueueBuffer.first() == it.uid }
            _currentSet.update { firstSet }
            return null
        }
        val index = setQueueBuffer.indexOf(currentSetUid)
        val remainingItems = if (index - 1 in setQueueBuffer.indices) setQueueBuffer.subList(
            0 ,
            index
        ) else emptyList()
        if (remainingItems.isEmpty()) {
            Log.d("Test" , "Workout completed")
            return null
        }
        val nextItem = _setSlots.value.firstOrNull { it.uid == remainingItems.last() }
        _currentSet.update { nextItem }

        if (nextItem != null) {
            updateCursorPosition(nextItem)
            updateCursorPosition(nextItem)
            return setQueueBuffer.indexOf(nextItem.uid)
        }
        return null
    }

    fun updateStatus(state : SetSlot , status : SetSlot.SetStatus) {
        Log.d("Test" , "Received set in viewmodel = ${state.uid}")
        viewModelScope.launch(Dispatchers.IO) {
            val newSet = state.copy(status = status.ordinal).apply { uid = state.uid }
            _setSlots.update {
                it.replace(newSet) {
                    it.uid == newSet.uid
                }
            }
            workoutRepository.updateSet(newSet).onFailure {
                it.printStackTrace()
            }
        }
    }

    fun updateCurrentSlot(newSet : SetSlot) {
        _currentSet.update { newSet }
        _btnSaveWorkoutState.update { UIState.Enabled }
        viewModelScope.launch {
            _session.value?.let {
                workoutRepository.updateSession(it.copy(cursorPosition = newSet.uid))
            }
        }
    }

    fun onCompleteSession() {
        viewModelScope.launch(Dispatchers.IO) {
            _btnSaveWorkoutState.update { UIState.Loading }
            _exerciseReports.update { emptyList() }
            delay(500)
            val noDefaultSet =
                _setSlots.value.none { it.status == SetSlot.SetStatus.Default.ordinal }
            if (!noDefaultSet) {
                //Show snackbar to tell the user that there sets not tracked
                _btnSaveWorkoutState.update { UIState.Error }
            }
            _session.value?.let {

                workoutRepository.updateSession(it.copy(status = WorkoutSession.Companion.SessionState.COMPLETED.ordinal))
                    .onFailure {
                        it.printStackTrace()
                    }.onSuccess {
                        _btnSaveWorkoutState.update { UIState.Completed }
                        val results = buildSessionReport()
                        _exerciseReports.emit(results)
                        Log.d("Test" , "Sent Results")
                    }
            }
        }
    }

    private fun buildSessionReport() : List<ExerciseReport> {
        val result = mutableListOf<ExerciseReport>()
        _exerciseSlots.value.onEach { slot ->
            val completedSets =
                _setSlots.value.filter { it.exerciseSlotUid == slot.uid && it.status == SetSlot.SetStatus.Completed.ordinal }
            val otherSets =
                _setSlots.value.filter { it.exerciseSlotUid == slot.uid && it.status != SetSlot.SetStatus.Completed.ordinal }
            result.add(
                ExerciseReport(
                    slot ,
                    setsCompleted = completedSets.count() ,
                    setsFailed = otherSets.count()
                )
            )
        }
        return result
    }
}




