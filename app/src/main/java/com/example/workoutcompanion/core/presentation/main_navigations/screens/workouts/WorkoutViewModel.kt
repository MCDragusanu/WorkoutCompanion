package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout_tracking.*
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.workout_designer.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.workout_designer.progression_overload.ProgressionOverloadManager
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel @Inject constructor(
    @Testing private val exerciseRepository: ExerciseRepository,
    @Testing private val userRepositoryImpl: ProfileRepositoryImpl,
    private val workoutRepository: WorkoutRepository,
    private val progressionManager: ProgressionOverloadManager
) : ViewModel() {

    // Private properties
    private lateinit var _currentUser : UserProfile

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _trainingParameters = TrainingParameters(
        uid = 0 ,
        userUid = guestProfile.uid ,
        programUid = -1 ,
        primaryCompoundSchema = ExerciseProgressionSchema.primaryCompoundSchema ,
        secondaryCompoundSchema = ExerciseProgressionSchema.secondaryCompoundSchema ,
        isolationSchema = ExerciseProgressionSchema.isolationSchema
    )

    // Initialization block
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            // Retrieve the current user profile
            val currentUserResult = userRepositoryImpl.getCurrentUser(this)
            if (currentUserResult.isFailure) {
                Log.d(
                    "Test" ,
                    (currentUserResult.exceptionOrNull()
                        ?: Exception("Failed to retrieve current user")).stackTraceToString()
                )
                _isLoading.update { false }
                return@launch
            }
            _currentUser = currentUserResult.getOrNull() ?: guestProfile

            // Get user workouts from the workout repository
            val userWorkouts = workoutRepository.getWorkouts(_currentUser.uid)
            userWorkouts.onEach { workoutMetadata ->
                val workout = Workout(workoutMetadata)
                // Get exercise slots for each workout
                val slots = async { workoutRepository.getSlotsForWorkout(workoutMetadata.uid) }

                slots.await().onEach { slot ->
                    // Get weeks for each exercise slot
                    val weeks = workoutRepository.getWeeksForSlot(slot)

                    weeks.onEach { week ->
                        // Get sets for each week
                        val sets = async { workoutRepository.getSetsForWeek(week) }

                        // Add sets to the _setList
                        _setList.update {
                            it.addNewEntry(week.uid , sets.await())
                        }
                    }
                    workout.addEntry(slot , weeks)
                }
                _workouts.update { it + workout }
            }
            _isLoading.update { false }
        }
    }




    // Function to handle the submission of a new workout
    fun onSubmittedWorkout(exercises : List<Exercise>) {
        viewModelScope.launch(Dispatchers.IO) {
            val workoutUid = System.currentTimeMillis()

            // Create metadata for the new workout
            val workoutMetadata = WorkoutMetadata(
                uid = workoutUid ,
                ownerUid = guestProfile.uid ,
                name = "Workout ${_workouts.value.size + 1}" ,
                description = "" ,
                dayOfWeek = _workouts.value.size
            )

            val workout = Workout(metadata = workoutMetadata)

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
                    category = it.exerciseCategory.ordinal ,
                    muscleGroups = "" ,
                    exerciseUid = it.uid ,
                    index = index
                )

                // Save the exercise slot in the workout repository
                workoutRepository.addExerciseSlot(slot)

                // Get the latest one-rep max value for the exercise from the repository
                val oneRepMax = workoutRepository.getLatestOneRepMax(it.uid , _currentUser.uid)

                if (oneRepMax == null) {
                    // TODO: Prompt the user to enter a one-rep max value
                    val setList = emptyList<SetSlot>()

                    val weekUid = System.currentTimeMillis()

                    startingPoint = Week(
                        uid = weekUid ,
                        exerciseSlotUid = slotUid ,
                        index = 0 ,
                        reps = 0 ,
                        sets = 0 ,
                        weightInKgs = 0.0
                    )

                    workoutRepository.addWeek(startingPoint)

                    _setList.update {
                        it.addNewEntry(weekUid , setList)
                    }

                    workout.addEntry(slot , listOf(startingPoint))
                }

                oneRepMax?.let {
                    val weekUid = System.currentTimeMillis()

                    // Find the starting point for the exercise progression using the one-rep max
                    startingPoint = progressionManager.findStartingPoint(
                        weekUid ,
                        exerciseSlotUid = slotUid ,
                        oneRepMax = it ,
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

                    // Add the set list to the _setList variable
                    _setList.update {
                        it.addNewEntry(startingPoint.uid , setList)
                    }
                    workout.addEntry(slot , listOf(startingPoint))
                }
            }
            _workouts.update { it + workout }
        }
    }

    // MutableStateFlow to hold the list of workouts
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts = _workouts.asStateFlow()

    // SetList to hold sets for each week
    private val _setList = MutableStateFlow(SetList())
    val setList = _setList.asStateFlow()

    // Function to get the appropriate schema based on exercise category
    private infix fun getSchema(int : Int) = when (int) {
        Exercise.Companion.ExerciseCategory.Isolation.ordinal -> _trainingParameters.isolationSchema
        Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> _trainingParameters.secondaryCompoundSchema
        else -> _trainingParameters.primaryCompoundSchema
    }

    fun onSetModified(
        week : Week ,
        slot : SetSlot ,
        field : Field.Reps ,
        field2 : Field.Weight
    ) : Int? {

        Log.d("Test" , "Set will be modified , reps = ${field.content} weight = ${field2.content}")
        val value = analiseRepField(field) ?: return R.string.error_invalid_interger
        val value2 = analiseWeightField(field2) ?: return R.string.error_invalid_decimal

        val newSet = slot.copy(weightInKgs = value2 , reps = value).apply { this.uid = slot.uid }
        _setList.update {
            it.editSet(newSet , week.uid)
        }

        viewModelScope.launch {
            workoutRepository.updateSet(newSet)
        }
        return null


    }

    fun onProvidedStartingPoint(
        exerciseSlot : ExerciseSlot ,
        reps : Field.Reps ,
        weight : Field.Weight ,
        week : Week
    ) : Pair<Pair<Field.Reps , Int?> , Pair<Field.Weight , Int?>> {
        val weightValue = analiseWeightField(weight)
        val repsValue = analiseRepField(reps)
        val weightState =
            Pair(weight , if (weightValue == null) R.string.error_invalid_decimal else null)
        val repsState =
            Pair(reps , if (repsValue == null) R.string.error_invalid_interger else null)

        if (weightValue != null && repsValue != null) {
            viewModelScope.launch(Dispatchers.IO) {

                val oneRepMax = OneRepMax(
                    exerciseUid = exerciseSlot.exerciseUid ,
                    userUid = _currentUser.uid ,
                    progressionManager.estimate1RepMaxInKgs(weightValue , repsValue) ,
                    timeStamp = System.currentTimeMillis()
                )

                workoutRepository.addOneRepMax(oneRepMax)

                val startingPoint =
                    progressionManager.findStartingPoint(
                        0 ,
                        0 ,
                        oneRepMax = oneRepMax ,
                        desiredNumberOfSets = 4 ,
                        schema = getSchema(exerciseSlot.category)
                    )

                val workout =
                    _workouts.value.firstOrNull() { it.metadata.uid == exerciseSlot.workoutUid }
                workout?.let {
                    Log.d("Test" , "Workout found  = ${it.metadata.uid}")
                    val newWeek = week.copy(
                        sets = startingPoint.sets ,
                        reps = startingPoint.reps ,
                        weightInKgs = startingPoint.weightInKgs
                    )

                    val newWorkout = workout.updateWeek(
                        new = newWeek ,
                        exerciseSlot
                    )

                    val setList = mutableListOf<SetSlot>()

                    repeat(newWeek.sets) {
                        val set = SetSlot(
                            weightInKgs = newWeek.weightInKgs ,
                            reps = newWeek.reps ,
                            index = it ,
                            weekUid = newWeek.uid
                        )
                        setList.add(set)
                    }


                    workoutRepository.addSets(*setList.toTypedArray())
                    Log.d("Test" , "Sets have been saved")
                    workoutRepository.updateWeek(newWeek)
                    Log.d("Test" , "Week has been updated")

                    Log.d("Test" , "Initial amount"+_setList.value.getSets(newWeek.uid).size.toString() )
                    _setList.update {
                        it.addNewEntry(newWeek.uid , setList)
                    }
                    Log.d("Test" , "Final amount"+_setList.value.getSets(newWeek.uid).size.toString())
                    _workouts.update {
                        it.replace(newWorkout) {old->
                            old.metadata.uid == newWorkout.metadata.uid
                        }
                    }
                }
            }
        }


        return Pair(repsState , weightState)
    }

    private fun analiseRepField(reps : Field.Reps) : Int? {
        return try {
            reps.content.toInt()
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    fun analiseWeightField(field : Field) : Double? {
        return try {
            field.content.toDouble()
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    fun onAddWeek(slot : ExerciseSlot , previousWeek : Week) {
        val newWeekUid = System.currentTimeMillis()
        val newWeek = progressionManager.generateSolution(
            previousWeek ,
            progressionSchema = getSchema(slot.category)
        ).copy(uid = newWeekUid , exerciseSlotUid = slot.uid)
        viewModelScope.launch(Dispatchers.IO) {
            workoutRepository.addWeek(newWeek)
            val setList = mutableListOf<SetSlot>()
            repeat(newWeek.sets) {
                val set = SetSlot(
                    weightInKgs = newWeek.weightInKgs ,
                    reps = newWeek.reps ,
                    index = it ,
                    weekUid = newWeek.uid
                )
                setList.add(set)
            }
            workoutRepository.addSets(sets = setList.toTypedArray())
            _setList.update {
                it.addNewEntry(newWeek.uid , setList)
            }
            val workout = _workouts.value.firstOrNull {
                it.metadata.uid == slot.workoutUid
            }
            workout?.let { new ->
                _workouts.update {
                    it.replace(new.addWeek(newWeek , slot)) {
                        it.metadata.uid == new.metadata.uid
                    }
                }

            }
        }
    }
}