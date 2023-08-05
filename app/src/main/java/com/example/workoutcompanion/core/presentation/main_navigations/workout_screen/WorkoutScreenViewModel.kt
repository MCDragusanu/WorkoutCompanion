package com.example.workoutcompanion.core.presentation.main_navigations.workout_screen

import android.util.Log
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
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.use_cases.GenerateSets
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkoutScreenViewModel @Inject constructor(private val progressionManager:ProgressionOverloadManager ,
                                                 private val workoutRepository : WorkoutRepository ,
                                                 @Testing
                                                 private val userRepository  : ProfileRepository,
                                                 @Testing
                                                 private val exerciseRepository : ExerciseRepository,
                                                 ):ViewModel() {

    private val _bottomSheetIsLoading = MutableStateFlow(true)
    val bottomSheetIsLoading = _bottomSheetIsLoading.asStateFlow()

    private val _exerciseCollection = MutableStateFlow<List<Exercise>>(emptyList())
    val exerciseCollection = _exerciseCollection.asStateFlow()

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

    private var _userUid : String? = null
    fun retrieveProfile(uid : String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getProfileFromLocalSource(uid , this).onSuccess {
                _profile = it
                if (_profile == null) {
                    Log.d("Test" , "No profile retrieved")
                }
                _profile?.let {
                    Log.d("Test" , "Profile retrieved = ${it.firstName}")
                    _trainingParameters =workoutRepository.getTrainingParameters(it.uid).getOrNull()
                }
            }.onFailure {
                Log.d("Test" , it.stackTraceToString())
            }
        }
    }


    fun retrieveWorkout(workoutUid : Long) {
        _workoutUid = workoutUid
        viewModelScope.launch(Dispatchers.IO) {
            val metadatata = workoutRepository.getWorkoutByUid(_workoutUid)


            if (metadatata == null) {
                Log.d("Test" , "No workout found")
                return@launch
            }
            val slots = workoutRepository.getSlotsForWorkout(_workoutUid)


            val weeks = mutableListOf<Week>()

            slots.onEach {
                weeks += workoutRepository.getWeeksForSlot(it)
            }
            val setList = mutableListOf<SetSlot>()
            weeks.onEach {
                val sets = workoutRepository.getSetsForWeek(it)
                setList += sets
            }

            _metadata.update { metadatata }
            _sets.update { setList }
            _weeks.update { weeks }
            _exerciseSlots.update { slots }
            _isLoading.update { false }
        }
    }

    fun onAddProgression(slot : ExerciseSlot) {

        viewModelScope.launch(Dispatchers.IO) {

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


        }
    }

    fun onSetChanged(set : SetSlot) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = _sets.value.replace(set) {
                it.uid == set.uid
            }
            _sets.update { updated }
            workoutRepository.updateSet(set)
        }
    }

    fun deleteExerciseSlot(slot : ExerciseSlot) {
        viewModelScope.launch {

            _exerciseSlots.update {
                it.filter { it.uid != slot.uid }
            }
            workoutRepository.deleteExerciseSlot(slot)
        }
    }


    fun updateColors(pair : Pair<Color , Color>) {
        viewModelScope.launch {
            _metadata.update {
                it.copy(
                    gradientStart = pair.first.hashCode() ,
                    gradientEnd = pair.second.hashCode()
                )
            }
            workoutRepository.updateMetadata(_metadata.value)
        }
    }

    fun updateMetadata(metadata : WorkoutMetadata) {
        viewModelScope.launch {
            _metadata.update { metadata }
            workoutRepository.updateMetadata(metadata)
        }
    }

    fun onSearchExercise(text : String) {
        viewModelScope.launch {
            _bottomSheetIsLoading.update { true }
            exerciseRepository.getCachedDatabase().onSuccess { result ->
                _exerciseCollection.update {
                    result.filter { it.exerciseName.contains(text , true) }.map { it.toExercise() }
                }
                _bottomSheetIsLoading.update { false }
            }.onFailure {
                Log.d("Test" , it.stackTraceToString())
            }
        }
    }

    fun removeSet(set : SetSlot) {
        viewModelScope.launch(Dispatchers.IO) {


            val newList = _sets.value.minus(set)

            _sets.update { newList }
            workoutRepository.removeSet(set)
        }
    }

    fun addNewSet(reps : Int , weight : Double , week : Week) {
        viewModelScope.launch(Dispatchers.IO) {
            val setIndex = _sets.value.count { it.weekUid == week.uid }
            val set = SetSlot(weightInKgs = weight , reps = reps , week.uid , setIndex)
            _sets.update { it + set }
            workoutRepository.addSets(sets = arrayOf(set))
        }
    }

    fun addExercise(exercise : Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            val lastIndex = _exerciseSlots.value.maxOfOrNull { it.index }?:-1
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

                val oneRepMaxWeight = workoutRepository.getLatestOneRepMax(exercise.uid , it.uid)

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
        }
    }
}
