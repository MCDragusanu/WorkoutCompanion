package com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.extentions.replace

import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout.WorkoutRepository
import com.example.workoutcompanion.core.data.workout.WorkoutRepositoryException
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.domain.use_cases.GenerateSets
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TrainingProgramViewModel @Inject constructor(
    @Testing
    private val profileRepository: ProfileRepository ,

    private val workoutRepository: WorkoutRepository ,
    private val progressionManager: ProgressionOverloadManager
) : ViewModel() {


    private val _workouts = MutableStateFlow<List<WorkoutMetadata>>(emptyList())
    val workouts = _workouts.asStateFlow()

    private val _appState = MutableStateFlow<WorkoutCompanionAppState?>(null)
    val appState = _appState.asStateFlow()

    private val _snackbarChannel = Channel<SnackbarEvent?>()
    val snackbarChannel = _snackbarChannel.receiveAsFlow()

    fun handleError(exception : Throwable) {
        exception.printStackTrace()
        viewModelScope.launch {
            _snackbarChannel.send(
                SnackbarEvent(
                    exception.localizedMessage ?: "Unknown Error" ,
                    type = SnackbarEvent.SnackbarType.Error
                )
            )
        }
    }
    fun setAppState(initial:WorkoutCompanionAppState?) {

        _appState.update { initial }
        _appState.value?.let { appState ->
            viewModelScope.launch(Dispatchers.IO) {
                workoutRepository.getWorkouts(appState.userProfile.uid)
                    .onFailure { handleError(it) }.onSuccess {
                        it.sortedBy { it.dayOfWeek }.onEach { newWorkout ->
                            _workouts.update { it + newWorkout }
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
                ownerUid = appState.value?.userProfile?.uid ?: guestProfile.uid ,
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
                    appState.value?.userProfile?.uid ?: guestProfile.uid
                ).onFailure { handleError(it) }.getOrNull()


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
                    workoutRepository.addWeek(startingPoint).onFailure { handleError(it) }



                    // Generate sets for the starting point
                    val setList =GenerateSets().execute(startingPoint ,getSchema(slot.category))

                    // Add the sets to the workout repository
                    workoutRepository.addSets(*setList.toTypedArray()).onFailure { handleError(it) }
                }
            }
            _workouts.update {
                it + workoutMetadata
            }
            withContext(Dispatchers.Main.immediate) {
                _snackbarChannel.send(
                    SnackbarEvent(
                        "Workout Created!" ,
                        SnackbarEvent.SnackbarType.Positive
                    )
                )
            }
        }
    }

    private inline fun getSchema(int : Int) = when (int) {
        Exercise.Companion.ExerciseCategory.Isolation.ordinal -> appState.value?.trainingParameters?.isolationSchema
            ?: ExerciseProgressionSchema.isolationSchema
        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> appState.value?.trainingParameters?.secondaryCompoundSchema
            ?: ExerciseProgressionSchema.secondaryCompoundSchema
        else -> appState.value?.trainingParameters?.primaryCompoundSchema
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



    fun updateSchema(appliedTo : Int , schema : ExerciseProgressionSchema) {
        _appState.value?.let {
            val newParameters : TrainingParameters = when (appliedTo) {
                Exercise.Companion.ExerciseCategory.PrimaryCompound.ordinal -> it.trainingParameters.copy(
                    primaryCompoundSchema = schema
                )
                Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> it.trainingParameters.copy(
                    secondaryCompoundSchema = schema
                )
                else -> it.trainingParameters.copy(isolationSchema = schema)
            }
            _appState.update { it!!.copy(trainingParameters = newParameters) }
            viewModelScope.launch(Dispatchers.IO) {
                workoutRepository.updateSchema(
                    schema ,
                    newParameters.uid
                )
            }
        }
    }

    fun showTooManyWorkouts() {
        viewModelScope.launch {
            _snackbarChannel.send(
                SnackbarEvent(
                    "You have too many workouts created . The maximum is 7!" ,
                    type = SnackbarEvent.SnackbarType.Error
                )
            )
        }
    }

    class SnackbarEvent(val string:String , val type:SnackbarType){
        enum class SnackbarType{
            Neutral,
            Positive,
            Error;
        }
    }
}