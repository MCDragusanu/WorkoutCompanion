package com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.di.ComponentType
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
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.use_cases.GenerateSets
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.domain.use_cases.GenerateWorkoutSession
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkoutEditorViewModel @Inject constructor(private val progressionManager:ProgressionOverloadManager ,
                                                 private val workoutRepository : WorkoutRepository ,
                                                 @ComponentType(false)
                                                 private val appStateManager : AppStateManager ,
                                                 @ComponentType(false)
                                                 private val userRepository  : ProfileRepository ,
                                                 @ComponentType(false)
                                                 private val exerciseRepository : ExerciseRepository ,
                                                 ):ViewModel() {

    private val _bottomSheetIsLoading = MutableStateFlow(true)
    val bottomSheetIsLoading = _bottomSheetIsLoading.asStateFlow()

    private val _exerciseCollection = MutableStateFlow<List<Exercise>>(emptyList())
    val exerciseCollection = _exerciseCollection.asStateFlow()

    private val _errorChannel = MutableSharedFlow<String>()
    val errorChannel = _errorChannel.asSharedFlow()
    
    private var _workoutUid : Long = -1

    private var _trainingParameters :TrainingParameters?= null

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


    private val _weeks = MutableStateFlow<List<Week>>(emptyList())
    val weeks =
        _weeks.asStateFlow()

    private val _sets = MutableStateFlow<List<SetSlot>>(emptyList())
    val sets = _sets.asStateFlow()


    private var _profile : UserProfile? = null

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()



    private var appState: WorkoutCompanionAppState? = null
    fun retrieveAppState(userUid:String) {
        viewModelScope.launch(Dispatchers.IO) {
            appStateManager.getAppState(userUid).collect { newState ->
                if (newState == null) {
                    Log.d("Test" , "DatabaseScreen::Current App State is null")
                }
                appState = newState

                newState?.let {
                    _profile = it.userProfile
                    _trainingParameters = it.trainingParameters
                    Log.d("Test" , "Workout Editor ViewModel :: Current App State has been updated")
                    Log.d("Test" , "Received user = ${it.userProfile.uid}" )
                }
            }
        }
    }



    
    private fun onError(e:Exception){
        viewModelScope.launch { 
            _errorChannel.emit(e.localizedMessage?:"Unknown error has occurred")
        }
    }


    fun retrieveWorkout(workoutUid : Long) {
        _workoutUid = workoutUid
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val metadata = workoutRepository.getWorkoutByUid(_workoutUid).onFailure { onError(Exception(it)) }.getOrNull()


                if (metadata == null) {
                    Log.d("Test" , "No workout found")
                    return@launch
                }
                workoutRepository.getSlotsForWorkout(_workoutUid).onFailure {
                    onError(Exception(it))
                }.onSuccess { slots ->
                    val weeks = mutableListOf<Week>()

                    slots.onEach {
                        weeks += workoutRepository.getWeeksForSlot(it).getOrNull()?: emptyList()
                    }
                    val setList = mutableListOf<SetSlot>()
                    weeks.onEach {
                        val sets = workoutRepository.getSetsForWeek(it).getOrNull()?: emptyList()
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

    fun onAddProgression(slot : ExerciseSlot) {

        viewModelScope.launch(Dispatchers.IO) {

            try{
                val previousWeek =
                    _weeks.value.filter { it.exerciseSlotUid == slot.uid }.maxByOrNull { it.index }

                if (previousWeek == null) {
                    Log.d("Test" , "Week not found")
                    return@launch
                }

                val newWeek =
                    progressionManager.generateSolution(previousWeek , getSchema(slot.category))
                        .copy(uid = System.currentTimeMillis() , exerciseSlotUid = slot.uid)

                val newSets = GenerateSets().execute(newWeek)

                workoutRepository.addWeek(newWeek)
                workoutRepository.addSets(*newSets.toTypedArray())

                _exerciseSlots.update { it }
                _sets.update { it + newSets }
                _weeks.update { it + newWeek }
            }catch (e:Exception){
                onError(e)
            }

        }
    }

    private inline fun getSchema(int : Int) = when (int) {
        Exercise.Companion.ExerciseCategory.Isolation.ordinal -> _trainingParameters?.isolationSchema?: ExerciseProgressionSchema.isolationSchema
        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> _trainingParameters?.secondaryCompoundSchema?: ExerciseProgressionSchema.secondaryCompoundSchema
        else -> _trainingParameters?.primaryCompoundSchema?: ExerciseProgressionSchema.primaryCompoundSchema
    }


    fun onSubmittedStartingPoint(
        slot : ExerciseSlot ,
        reps : Int ,
        weight : Double
    ) {
        viewModelScope.launch {

            try{
                val oneRepMax =
                    progressionManager.estimate1RepMaxInKgs(weightInKgs = weight , reps = reps)


                val startingPoint = progressionManager.findStartingPoint(
                    uid = System.currentTimeMillis() ,
                    exerciseSlotUid = slot.uid ,
                    oneRepMaxWeight = oneRepMax ,
                    desiredNumberOfSets = 4 ,
                    schema = getSchema(slot.category)
                )

                val sets = GenerateSets().execute(startingPoint)

                workoutRepository.addOneRepMax(
                    OneRepMax(
                        slot.exerciseUid ,
                        userUid = _profile?.uid ?: guestProfile.uid ,
                        weightKgs = oneRepMax ,
                        timeStamp = System.currentTimeMillis()
                    )
                )
                workoutRepository.addWeek(startingPoint)
                workoutRepository.addSets(*sets.toTypedArray())

                _exerciseSlots.update { it }
                _sets.update { it + sets }
                _weeks.update { it + startingPoint }
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun onSetChanged(set : SetSlot) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val updated = _sets.value.replace(set) {
                    it.uid == set.uid
                }
                _sets.update { updated }
                workoutRepository.updateSet(set)
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun deleteExerciseSlot(slot : ExerciseSlot) {
        viewModelScope.launch {

            try{
                _exerciseSlots.update {
                    it.filter { it.uid != slot.uid }
                }
                workoutRepository.deleteExerciseSlot(slot)
            }catch (e:Exception){
                onError(e)
            }
        }
    }


    fun updateColors(pair : Pair<Color , Color>) {
        viewModelScope.launch {
            try{
                _metadata.update {
                    it.copy(
                        gradientStart = pair.first.hashCode() ,
                        gradientEnd = pair.second.hashCode()
                    )
                }
                workoutRepository.updateMetadata(_metadata.value)
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun updateMetadata(metadata : WorkoutMetadata) {
        viewModelScope.launch {
            try{
                _metadata.update { metadata }
                workoutRepository.updateMetadata(metadata)
            }catch (e:Exception){
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

            try{
                val newList = _sets.value.minus(set)

                _sets.update { newList }
                workoutRepository.removeSet(set)
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun addNewSet(reps : Int , weight : Double , week : Week) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val setIndex = _sets.value.count { it.weekUid == week.uid }
                val set = SetSlot(weightInKgs = weight , reps = reps , week.uid , setIndex)
                _sets.update { it + set }
                workoutRepository.addSets(sets = arrayOf(set))
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun addExercise(exercise : Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
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
                _profile?.let {

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

                        val sets = GenerateSets().execute(startingPoint)

                        workoutRepository.addSets(*sets.toTypedArray())
                        workoutRepository.addWeek(startingPoint)
                        _sets.update { it + sets }
                        _weeks.update { it + startingPoint }

                    }
                    workoutRepository.addExerciseSlot(slot)
                    _exerciseSlots.update { it + slot }
                }
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun onStartWorkout(onCreatedSession:(uid:Long)->Unit) {
        viewModelScope.launch {
            val isValid = checkIfStartedPointsProvided()
            if(!isValid) {
                onError(IllegalArgumentException("Please provide a starting point for every exercise"))
                return@launch
            }
            GenerateWorkoutSession().execute(
                workoutMetadata = _metadata.value ,
                workoutRepository
            ).onFailure {
                onError(Exception(it))
            }.onSuccess {
                workoutRepository.addSession(it)
                onCreatedSession(it.uid)
            }
        }
    }

    private fun checkIfStartedPointsProvided():Boolean{
        var result = true

        _exerciseSlots.value.onEach {slot->
            val hasStartingPoint = _weeks.value.any { week->week.exerciseSlotUid == slot.uid }
            if(!hasStartingPoint) result = false
        }
        return result
    }
}
