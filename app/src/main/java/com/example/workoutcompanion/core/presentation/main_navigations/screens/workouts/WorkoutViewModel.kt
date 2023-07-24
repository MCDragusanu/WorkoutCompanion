package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.workout.Workout
import com.example.workoutcompanion.core.domain.model.workout.WorkoutMetadata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel @Inject constructor() :ViewModel() {


    init {
        //TODO
        //implement workout database to store all the workout related database
        //when the contructor is called , fetch the current workouts
        //and update the ui screen
    }

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts = _workouts.asStateFlow()

    private val daysOfWeek = DayOfWeek.values().map { it.ordinal }.toMutableList()

    fun onSubmittedWorkout(content : List<Exercise>) {

        val dayOfWeek = daysOfWeek.lastOrNull()
        if (dayOfWeek == null) {
            //TODO make a popup that says you can't have more workouts > 7
            Log.d("Test" , "There are no available days")
            return
        }
        val emptyWorkoutPosition = _workouts.value.indexOfFirst { it.exerciseList.isEmpty() }
        if (emptyWorkoutPosition == -1) {
            daysOfWeek.remove(dayOfWeek)
            val metadata = WorkoutMetadata(
                uid = System.currentTimeMillis() ,
                ownerId = guestProfile.uid ,
                workoutName = "My Workout ${_workouts.value.size + 1}" ,
                scheduledDay = dayOfWeek ,
                scheduledHour = "10 AM"
            )
            viewModelScope.launch { _workouts.emit(_workouts.value + Workout(metadata , content)) }
        } else {
            val emptyWorkout = _workouts.value[emptyWorkoutPosition]
            val mutableWorkouts = _workouts.value.toMutableList()
            mutableWorkouts[emptyWorkoutPosition] = emptyWorkout.copy(exerciseList = content)
            _workouts.update { mutableWorkouts }
        }

        Log.d("Test" , "Workout was added!")
    }

    fun onCreateWorkout() {
        val dayOfWeek = daysOfWeek.lastOrNull()
        if (dayOfWeek == null) {
            //TODO make a popup that says you can't have more workouts > 7
            Log.d("Test" , "There are no available days")
            return
        }
        daysOfWeek.remove(dayOfWeek)
        val metadata = WorkoutMetadata(
            uid = System.currentTimeMillis() ,
            ownerId = guestProfile.uid ,
            workoutName = "My Workout ${_workouts.value.size + 1}" ,
            scheduledDay = dayOfWeek ,
            scheduledHour = "10 AM"
        )
        _workouts.update { it + Workout(metadata , emptyList()) }
    }
}