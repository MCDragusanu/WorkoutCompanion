package com.example.workoutcompanion.workout

import com.example.workoutcompanion.exercise.ExerciseMovement
import com.example.workoutcompanion.exercise.MuscleGroupsSimple

class WorkoutBluePrint(val UID:Int,
                       val name:String,
                       val trainedMuscles:List<Pair<MuscleGroupsSimple, List<ExerciseMovement>>> = emptyList()) {

}