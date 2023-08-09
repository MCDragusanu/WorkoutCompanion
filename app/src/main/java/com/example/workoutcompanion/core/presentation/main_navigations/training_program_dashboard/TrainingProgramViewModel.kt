package com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.di.ComponentType
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrainingProgramViewModel @Inject constructor(
    @ComponentType(false)
    private val profileRepository: ProfileRepository ,
    @ComponentType(false)
    private val appStateManager : AppStateManager ,
    private val workoutRepository: WorkoutRepository ,
    private val progressionManager: ProgressionOverloadManager
) : ViewModel() {


    private val _workouts = MutableStateFlow<List<WorkoutMetadata>>(emptyList())
    val workouts = _workouts.asStateFlow()

    private var _profile : UserProfile? = null


    private var _trainingParameters = MutableStateFlow<TrainingParameters?>(null)
    val trainingParameters = _trainingParameters.asStateFlow()


    private var _userUid : String? = null


    private var appState: WorkoutCompanionAppState? = null
    fun retrieveAppState(userUid:String) {
        viewModelScope.launch(Dispatchers.IO) {
            appStateManager.getAppState(userUid).collect { newState ->
                if (newState == null) {
                    Log.d("Test" , "Training Program ViewModel ::Current App State is null")
                }
                appState = newState
                newState?.let { new ->
                    _profile = new.userProfile
                    _trainingParameters.update { new.trainingParameters }
                    Log.d(
                        "Test" ,
                        "Training Program ViewModel ::Current App State has been updated"
                    )
                    Log.d("Test" , "Received user = ${new.userProfile.uid}" )
                }
                _profile?.let { user ->
                    workoutRepository.getWorkouts(user.uid).onFailure {
                        it.printStackTrace()
                    }.onSuccess {
                        it.sortedBy { it.dayOfWeek }
                            .onEach { metadata ->
                                _workouts.update {
                                    it + metadata
                                }
                            }
                    }

                    _trainingParameters.update {
                        workoutRepository.getTrainingParameters(user.uid).getOrNull()
                    }
                }
            }
        }
    }



    fun onSubmittedWorkout(exercises : List<Exercise>) {
        viewModelScope.launch(Dispatchers.IO) {
            val workoutUid = System.currentTimeMillis()

            // Create metadata for the new workout
            val workoutMetadata = WorkoutMetadata(
                uid = workoutUid ,
                ownerUid = appState?.userProfile?.uid?: guestProfile.uid ,
                name = "Workout #${_workouts.value.size + 1}" ,
                description = "" ,
                dayOfWeek = _workouts.value.size
            )


            var startingPoint : Week
            // Add the workout metadata to the workout repository
            workoutRepository.addWorkoutMetadata(workoutMetadata)

            exercises.onEachIndexed { index , it ->
                // Generate a unique identifier for the exercise slot
                val slotUid = System.currentTimeMillis()

                // Create an exercise slot object
                val slot = ExerciseSlot(
                    uid = slotUid ,
                    workoutUid = workoutUid ,
                    exerciseName = it.exerciseName ,
                    type = it.movement.type ,
                    isBodyWeight = it.isBodyWeight ,
                    category = it.exerciseCategory.ordinal ,
                    muscleGroups = buildString {
                        (it.movement.primaryMuscleGroups + it.movement.secondaryMuscleGroups).map { it.first.ordinal }
                            .onEach {
                                append(it)
                                append("/")
                            }
                    } ,
                    exerciseUid = it.uid ,
                    index = index
                )

                // Save the exercise slot in the workout repository
                workoutRepository.addExerciseSlot(slot)

                // Get the latest one-rep max value for the exercise from the repository
                val oneRepMax = workoutRepository.getLatestOneRepMax(
                    it.uid ,
                    _profile?.uid ?: guestProfile.uid
                ).getOrNull()


                oneRepMax?.let {
                    val weekUid = System.currentTimeMillis()

                    // Find the starting point for the exercise progression using the one-rep max
                    startingPoint = progressionManager.findStartingPoint(
                        weekUid ,
                        exerciseSlotUid = slotUid ,
                        oneRepMaxWeight = it.weightKgs ,
                        schema = getSchema(slot.category) ,
                        desiredNumberOfSets = 4
                    )

                    // Add the new week to the workout repository
                    workoutRepository.addWeek(startingPoint)

                    val setList = mutableListOf<SetSlot>()

                    // Generate sets for the starting point
                    repeat(startingPoint.sets) {
                        val set = SetSlot(
                            weightInKgs = startingPoint.weightInKgs ,
                            reps = startingPoint.reps ,
                            index = it ,
                            weekUid = startingPoint.uid
                        )
                        setList.add(set)
                    }


                    // Add the sets to the workout repository
                    workoutRepository.addSets(*setList.toTypedArray())


                }

            }
            _workouts.update {
                it + workoutMetadata
            }
        }
    }

    private inline fun getSchema(int : Int) = when (int) {
        Exercise.Companion.ExerciseCategory.Isolation.ordinal -> _trainingParameters.value?.isolationSchema
            ?: ExerciseProgressionSchema.isolationSchema
        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> _trainingParameters.value?.secondaryCompoundSchema
            ?: ExerciseProgressionSchema.secondaryCompoundSchema
        else -> _trainingParameters.value?.primaryCompoundSchema
            ?: ExerciseProgressionSchema.primaryCompoundSchema
    }

    fun updateMetadata(newWorkout : WorkoutMetadata) {
        Log.d("Test" , "Metadata changed")
        _workouts.update {
            it.replace(newWorkout) {
                it.uid == newWorkout.uid
            }
        }
    }

    fun generateInitialParameters() {
        viewModelScope.launch(Dispatchers.IO) {
            _trainingParameters.update {
                val uid = System.currentTimeMillis()
                TrainingParameters(
                    uid = uid ,
                    userUid = _profile?.uid ?: guestProfile.uid ,
                    programUid = -1 ,
                    primaryCompoundSchema = ExerciseProgressionSchema.primaryCompoundSchema ,
                    secondaryCompoundSchema = ExerciseProgressionSchema.secondaryCompoundSchema ,
                    isolationSchema = ExerciseProgressionSchema.isolationSchema
                )

            }
            workoutRepository.createInitialParameters(_profile?.uid ?: guestProfile.uid).onFailure {

            }.onSuccess {

            }
        }
    }

    fun updateSchema(appliedTo : Int , schema : ExerciseProgressionSchema) {
        viewModelScope.launch {
            _trainingParameters.update {
                when (appliedTo) {
                    0 -> it?.copy(primaryCompoundSchema = schema.apply { this.uid = schema.uid })
                    1 -> it?.copy(secondaryCompoundSchema = schema.apply { this.uid = schema.uid })
                    else -> it?.copy(isolationSchema = schema.apply { this.uid = schema.uid })
                }

            }
            workoutRepository.updateSchema(schema , _trainingParameters.value?.uid ?: 0).onFailure {

            }.onSuccess {

            }
        }
    }

}