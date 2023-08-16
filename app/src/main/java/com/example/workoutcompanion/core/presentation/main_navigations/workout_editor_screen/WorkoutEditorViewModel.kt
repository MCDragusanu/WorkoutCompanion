package com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.extentions.replace

import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.domain.use_cases.GenerateWorkoutSession
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class WorkoutEditorViewModel @Inject constructor(private val progressionManager:ProgressionOverloadManager ,
                                                 private val workoutRepository : WorkoutRepository ,
                                                 @Testing
                                                 private val userRepository  : ProfileRepository ,
                                                 @Testing
                                                 private val exerciseRepository : ExerciseRepository ,
                                                 ):ViewModel() {

    private val _bottomSheetIsLoading = MutableStateFlow(true)
    val bottomSheetIsLoading = _bottomSheetIsLoading.asStateFlow()

    private val _exerciseCollection = MutableStateFlow<List<Exercise>>(emptyList())
    val exerciseCollection = _exerciseCollection.asStateFlow()

    private val _errorChannel = MutableSharedFlow<String>()
    val errorChannel = _errorChannel.asSharedFlow()

    private var _workoutUid : Long = -1



    private val _metadata = MutableStateFlow(
        WorkoutMetadata(
            -1 ,
            guestProfile.uid ,
            "Place holder" ,
            "" ,
            0 ,
            0 ,
            0 ,
            dayOfWeek = 0
        )
    )
    val metadata = _metadata.asStateFlow()

    private val _exerciseSlots = MutableStateFlow<List<ExerciseSlot>>(emptyList())
    val exerciseSlots = _exerciseSlots.asStateFlow()


    private val _btnStartWorkout = MutableStateFlow(false)
    val btnStartWorkout = _btnStartWorkout.asStateFlow()

    private val _weeks = MutableStateFlow<List<Week>>(emptyList())
    val weeks =
        _weeks.asStateFlow()

    private val _sets = MutableStateFlow<List<SetSlot>>(emptyList())
    val sets = _sets.asStateFlow()




    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _appState = MutableStateFlow<WorkoutCompanionAppState?>(null)
    val appState = _appState.asStateFlow()
    fun retrieveAppState(appState : WorkoutCompanionAppState?) {
        Log.d("Test" , "Received appState = ${appState.toString()}")
        _appState.update { appState }
    }


    private fun onError(e : Throwable) {
        viewModelScope.launch {
            _errorChannel.emit(e.localizedMessage ?: "Unknown error has occurred")
        }
    }
    fun sessionIsValid(uid:Long):Boolean{
        return (System.currentTimeMillis() - uid < 8 * 3600 * 1000)
    }

    fun retrieveWorkout(workoutUid : Long) {
        _workoutUid = workoutUid
        Log.d("Bug" , "retrieve Workout called once")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val metadata = workoutRepository.getWorkoutByUid(_workoutUid)
                    .onFailure { onError(Exception(it)) }.getOrNull()


                if (metadata == null) {
                    Log.d("Test" , "No workout found")
                    return@launch
                }
                val latestSession = workoutRepository.getLatestSessionUidForWorkout(metadata.uid)
                    .onFailure { onError(it) }.getOrNull()

                _btnStartWorkout.update { latestSession!=null && sessionIsValid(latestSession) }

                workoutRepository.getSlotsForWorkout(_workoutUid).onFailure {
                    onError(Exception(it))
                }.onSuccess { slots ->
                    val weeks = mutableListOf<Week>()

                    slots.onEach {
                        weeks += workoutRepository.getWeeksForSlot(it).getOrNull() ?: emptyList()
                    }
                    val setList = mutableListOf<SetSlot>()
                    weeks.onEach {
                        val sets = workoutRepository.getSetsForWeek(it).getOrNull() ?: emptyList()
                        setList += sets
                    }

                    _metadata.update { metadata }
                    _sets.update { setList }
                    _weeks.update { weeks }
                    _exerciseSlots.update { slots }
                    _isLoading.update { false }
                }


            } catch (e : Exception) {
                onError(e)
            }
        }
    }



    fun getSchema(int : Int) = when (int) {
        Exercise.Companion.ExerciseCategory.Isolation.ordinal -> appState.value?.trainingParameters?.isolationSchema
            ?: ExerciseProgressionSchema.isolationSchema
        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> appState.value?.trainingParameters?.secondaryCompoundSchema
            ?: ExerciseProgressionSchema.secondaryCompoundSchema
        else -> appState.value?.trainingParameters?.primaryCompoundSchema
            ?: ExerciseProgressionSchema.primaryCompoundSchema
    }


    fun onSubmittedStartingPoint(
        slot : ExerciseSlot ,
        reps : Int ,
        weight : Double
    ) {
        viewModelScope.launch {

            try {
                val oneRepMax =
                    progressionManager.estimate1RepMaxInKgs(weightInKgs = weight , reps = reps)


                val startingPoint = progressionManager.findStartingPoint(
                    uid = System.currentTimeMillis() ,
                    exerciseSlotUid = slot.uid ,
                    oneRepMaxWeight = oneRepMax ,
                    desiredNumberOfSets = 4 ,
                    schema = getSchema(slot.category)
                )

                val sets =progressionManager.generateSets(startingPoint , getSchema(slot.category))

                workoutRepository.addOneRepMax(
                    OneRepMax(
                        slot.exerciseUid ,
                        userUid = _appState.value?.userProfile?.uid ?: guestProfile.uid ,
                        weightKgs = oneRepMax ,
                        timeStamp = System.currentTimeMillis()
                    )
                )
                Log.d("Test" , "Created uids = ${sets.map { it.uid }}")
                workoutRepository.addWeek(startingPoint)
                workoutRepository.addSets(*sets.toTypedArray())

                _exerciseSlots.update { it }
                _sets.update { it + sets }
                _weeks.update { it + startingPoint }
            } catch (e : Exception) {
                onError(e)
            }
        }
    }



    fun onSetChanged(set : SetSlot) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updated = _sets.value.replace(set) {
                    it.uid == set.uid
                }
                _sets.update { updated }
                workoutRepository.updateSet(set)
            } catch (e : Exception) {
                onError(e)
            }
        }
    }

    fun deleteExerciseSlot(slot : ExerciseSlot) {
        viewModelScope.launch {

            try {


                val latestWeek = _weeks.value.filter { it.exerciseSlotUid == slot.uid }.maxByOrNull { it.index }

                if(latestWeek == null){
                    //It is a starting point
                    Log.d("Test" , "Deleting a starting point")
                }
                //remove the current Sets
                workoutRepository.deleteExerciseSlot(slot , latestWeek?.uid?:-1)

                val latestSession = workoutRepository.getLatestSessionUidForWorkout(_metadata.value.uid).onFailure { onError(it) }.getOrNull()

                if(latestSession == null){
                    Log.d("Test", "There is no session found")
                    return@launch
                }

                updateSession(latestSession)
                _exerciseSlots.update {
                    it.filter { it.uid != slot.uid }
                }
                _sets.update { it.filter { it.weekUid != (latestWeek?.uid ?: -1) } }

            } catch (e : Exception) {
                onError(e)
            }
        }
    }


    fun updateColors(pair : Pair<Color , Color>) {
        viewModelScope.launch {
            try {
                _metadata.update {
                    it.copy(
                        gradientStart = pair.first.hashCode() ,
                        gradientEnd = pair.second.hashCode()
                    )
                }
                workoutRepository.updateMetadata(_metadata.value)
            } catch (e : Exception) {
                onError(e)
            }
        }
    }

    fun updateMetadata(metadata : WorkoutMetadata) {
        viewModelScope.launch {
            try {
                _metadata.update { metadata }
                workoutRepository.updateMetadata(metadata)
            } catch (e : Exception) {
                onError(e)
            }
        }
    }

    fun onSearchExercise(text : String) {
        viewModelScope.launch {

            exerciseRepository.getCachedDatabase().onSuccess { result ->
                _exerciseCollection.update {
                    result.filter { it.exerciseName.contains(text , true) }.map { it.toExercise() }
                }
                _bottomSheetIsLoading.update { false }
            }.onFailure {
                onError(Exception(it))
            }
        }
    }

    fun removeSet(set : SetSlot) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val newList = _sets.value.minus(set)

                _sets.update { newList }
                workoutRepository.removeSet(set)
            } catch (e : Exception) {
                onError(e)
            }
        }
    }

    fun addNewSet(type : Int , reps : Int , weight : Double , week : Week) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val setIndex = _sets.value.count { it.weekUid == week.uid }
                val set =
                    SetSlot(
                        weightInKgs = weight ,
                        reps = reps ,
                        weekUid = week.uid ,
                        exerciseSlotUid = week.exerciseSlotUid ,
                        type = type ,
                        status =  SetSlot.SetStatus.Default.ordinal,
                       index=setIndex,

                    )
                _sets.update { it + set }
                workoutRepository.addSets(sets = arrayOf(set))
            } catch (e : Exception) {
                onError(e)
            }
        }
    }

    fun addExercise(exercise : Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lastIndex = _exerciseSlots.value.maxOfOrNull { it.index } ?: -1
                val slotUid = System.currentTimeMillis()
                val slot = ExerciseSlot(
                    uid = slotUid ,
                    workoutUid = metadata.value.uid ,
                    exerciseName = exercise.exerciseName ,
                    type = exercise.movement.type ,
                    category = exercise.exerciseCategory.ordinal ,
                    isBodyWeight = exercise.isBodyWeight ,
                    exerciseUid = exercise.uid ,
                    muscleGroups = buildString {
                        (exercise.movement.primaryMuscleGroups + exercise.movement.secondaryMuscleGroups).map { it.first.ordinal }
                            .onEach {
                                append("${it}")
                                append("/")
                            }
                    } ,
                    index = lastIndex + 1
                )
                _appState.value?.userProfile?.let {

                    val oneRepMaxWeight =
                        workoutRepository.getLatestOneRepMax(exercise.uid , it.uid).getOrNull()

                    oneRepMaxWeight?.let {
                        val startingPoint = progressionManager.findStartingPoint(
                            uid = System.currentTimeMillis() ,
                            exerciseSlotUid = slotUid ,
                            oneRepMaxWeight = it.weightKgs ,
                            desiredNumberOfSets = 4 ,
                            schema = getSchema(slot.category)
                        )

                        val sets = progressionManager.generateSets(startingPoint , getSchema(slot.category))

                        Log.d("Test" , "Add Exercise Set Uids = ${sets.map { it.uid }}")
                        workoutRepository.addSets(*sets.toTypedArray())
                        workoutRepository.addWeek(startingPoint)
                        _sets.update { it + sets }
                        _weeks.update { it + startingPoint }
                        Log.d("Test" , " Set Flow Uids = ${_sets.value.map { it.uid }}")
                    }
                    workoutRepository.addExerciseSlot(slot)
                    _exerciseSlots.update { it + slot }
                }
            } catch (e : Exception) {
                onError(e)
            }
        }
    }

    fun onStartWorkout(onCreatedSession : (uid : Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isValid = checkIfStartedPointsProvided()
            if (!isValid) {
                onError(IllegalArgumentException("Please provide a starting point for every exercise"))
                return@launch
            }

            val latestSession = workoutRepository.getLatestSessionUidForWorkout(_workoutUid)
                .onFailure { it.printStackTrace() }.getOrNull()

            when {
                latestSession == null || !sessionIsValid(latestSession) -> {
                    //create new session
                    Log.d("Test" , "No session found or is expired  uid = ${latestSession}")
                    val setList = mutableListOf<SetSlot>()
                    _exerciseSlots.value.onEach { slot ->
                        val latestWeek = _weeks.value.filter { it.exerciseSlotUid == slot.uid }
                            .maxBy { it.index }
                        val sets = workoutRepository.getSetsForWeek(latestWeek)
                            .onFailure { it.printStackTrace() }.getOrNull() ?: emptyList()
                        setList.addAll(sets)
                    }
                    val session = WorkoutSession(
                        uid = System.currentTimeMillis() ,
                        workoutUid = _workoutUid ,
                        ownerUid = _appState.value?.userProfile?.uid ?: guestProfile.uid,
                        slotList = WorkoutSession.buildUidString(_exerciseSlots.value.map { it.uid }),
                        setList = WorkoutSession.buildUidString(setList.map { it.uid.toLong() }) ,
                        cursorPosition = setList.firstOrNull()?.uid?:-1,
                        status = WorkoutSession.Companion.SessionState.STARTED.ordinal
                    )
                    workoutRepository.addSession(session)
                    withContext(Dispatchers.Main) {
                        onCreatedSession(session.uid)
                    }
                }

                else -> {
                    //session is still active
                    updateSession(latestSession)
                    Log.d("Test" , "Found Active Session")
                    withContext(Dispatchers.Main) {
                        onCreatedSession(latestSession)
                    }
                }
            }
        }
    }


    fun updateSession(sessionUid : Long) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _ , error ->
            onError(
                error
            )
        }) {
            Log.d("Test" , "Update Session called")
            val session =
                workoutRepository.getSession(sessionUid).onFailure { onError(it) }.getOrNull()
                    ?: throw Exception("Failed to retrieve active session")
            val slotsUids = _exerciseSlots.value.map { it.uid }
            val setsUid = mutableListOf<Long>()
            slotsUids.onEach { slotUid ->
                val latestWeek =
                    _weeks.value.filter { it.exerciseSlotUid == slotUid }.maxByOrNull { it.index }
                if (latestWeek == null) {
                    onError(IllegalArgumentException("Please provide a starting point for every exercise"))
                    return@launch
                }
                setsUid.addAll(_sets.value.filter { it.weekUid == latestWeek.uid }
                    .sortedBy { it.index }
                    .map { it.uid.toLong() })
            }

            Log.d("Test" , "old session = ${session} , sessionUid = ${session.uid}")
            val newSession = session.copy(
                cursorPosition = if(session.cursorPosition in setsUid) session.cursorPosition else setsUid.first(),
                setList = WorkoutSession.buildUidString(setsUid) ,
                slotList = WorkoutSession.buildUidString(slotsUids)
            )
            Log.d("Test" , "New session = ${newSession} , sessionUid =${newSession.uid}")


            workoutRepository.updateSession(newSession).onFailure {
                onError(it)
            }.onSuccess {
                Log.d("Test" , "Session updated")
            }
        }
    }


    private fun checkIfStartedPointsProvided() : Boolean {
        var result = true

        _exerciseSlots.value.onEach { slot ->
            val hasStartingPoint = _weeks.value.any { week -> week.exerciseSlotUid == slot.uid }
            if (!hasStartingPoint) result = false
        }
        return result
    }



    fun onEditRestTime(slot : ExerciseSlot , type : Int , value : Int) {

        val schema = getSchema(slot.category)
        Log.d("Test" , "Get Schema Uid = ${schema.uid} , type = ${type}")
        val newSchema = if (type == SetSlot.SetType.WarmUp.ordinal) schema.copy(warmUpSetRestTimeInSeconds = value)
            .apply { this.uid = schema.uid }
        else schema.copy(workingSetRestTimeInSeconds = value).apply { this.uid = schema.uid }
        viewModelScope.launch(Dispatchers.IO) {
            _appState.update {
                workoutRepository.updateSchema(newSchema , it?.trainingParameters?.uid ?: -1)
                it?.copy(
                    trainingParameters = when (slot.category) {
                        Exercise.Companion.ExerciseCategory.PrimaryCompound.ordinal -> it.trainingParameters.copy(
                            primaryCompoundSchema = newSchema.apply { uid = schema.uid }
                        )
                        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> it.trainingParameters.copy(
                            secondaryCompoundSchema = newSchema.apply { uid = schema.uid }
                        )
                        else -> it.trainingParameters.copy(
                            isolationSchema = newSchema.apply { uid = schema.uid }
                        )
                    }
                )
            }

        }

    }
}
