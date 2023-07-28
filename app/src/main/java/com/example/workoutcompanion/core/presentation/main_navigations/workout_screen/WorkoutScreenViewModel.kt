package com.example.workoutcompanion.core.presentation.main_navigations.workout_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout_tracking.WorkoutRepository
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.use_cases.GenerateSets
import com.example.workoutcompanion.workout_designer.progression_overload.ExerciseProgressionSchema
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
                                                 private val userRepository  : ProfileRepository):ViewModel() {
    private var _workoutUid : Long = -1

    private val _trainingParameters = TrainingParameters(
        uid = 0 ,
        userUid = guestProfile.uid ,
        programUid = -1 ,
        primaryCompoundSchema = ExerciseProgressionSchema.primaryCompoundSchema ,
        secondaryCompoundSchema = ExerciseProgressionSchema.secondaryCompoundSchema ,
        isolationSchema = ExerciseProgressionSchema.isolationSchema
    )

    private val _metadata = MutableStateFlow(
        WorkoutMetadata(
            -1 ,
            guestProfile.uid ,
            "Place holder" ,
            "" ,
            0 ,
            0
        )
    )
    val metadata = _metadata.asStateFlow()

    private val _exerciseSlots = MutableStateFlow<List<ExerciseSlot>>(emptyList())
    val exerciseSlots = _exerciseSlots.asStateFlow()
        .onEach { Log.d("Test" , "new ${it.size} exercise slots sent from viewModel") }

    private val _weeks = MutableStateFlow<List<Week>>(emptyList())
    val weeks =
        _weeks.asStateFlow().onEach { Log.d("Test" , "new ${it.size} weeks sent from viewModel") }

    private val _sets = MutableStateFlow<List<SetSlot>>(emptyList())
    val sets = _sets.asStateFlow()
        .onEach { Log.d("Test" , "new ${it.size} set slots sent from viewModel") }


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
                }
            }.onFailure {
                Log.d("Test" , it.stackTraceToString())
            }
        }
    }


    fun retrieveWorkout(workoutUid : Long) {
        _workoutUid = workoutUid
        viewModelScope.launch(Dispatchers.IO){
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
                setList += workoutRepository.getSetsForWeek(it)
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
        Exercise.Companion.ExerciseCategory.Isolation.ordinal -> _trainingParameters.isolationSchema
        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> _trainingParameters.secondaryCompoundSchema
        else -> _trainingParameters.primaryCompoundSchema
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
}
