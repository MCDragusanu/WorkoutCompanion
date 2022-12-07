package com.example.workoutcompanion.workout

import com.example.workoutcompanion.exercise.ExerciseMovement
import com.example.workoutcompanion.exercise.MuscleGroupsSimple

sealed class TrainingSplit(val trainingFrequency:Int,
                           val name:String,
                           val workoutBluePrints:List<WorkoutBluePrint>,
                           val workoutFrequency:Int = 2,
                           ) {

    object PPL : TrainingSplit(
        trainingFrequency = 6, name = "Push-Pull-Legs",
        workoutBluePrints = listOf(
            WorkoutBluePrint(
                UID = 0, trainedMuscles = listOf(
                    Pair(
                        MuscleGroupsSimple.Chest,
                        listOf(
                            ExerciseMovement.ChestPress,
                            ExerciseMovement.ChestPress,
                            ExerciseMovement.ChestFly
                        )
                    ),
                    Pair(
                        MuscleGroupsSimple.FrontDelts,
                        listOf(ExerciseMovement.ShoulderPress, ExerciseMovement.ShoulderRaise)
                    ),
                    Pair(
                        MuscleGroupsSimple.LateralDelts,
                        listOf(ExerciseMovement.ShoulderFly, ExerciseMovement.ShoulderFly)
                    ),
                    Pair(
                        MuscleGroupsSimple.Triceps,
                        listOf(ExerciseMovement.TricepsPushDown, ExerciseMovement.TricepsExtension)
                    )
                ),
                name = "Push-Day"
            ),
            WorkoutBluePrint(
                UID = 1, trainedMuscles = listOf(
                    Pair(
                        MuscleGroupsSimple.Back,
                        listOf(
                            ExerciseMovement.Hinge,
                            ExerciseMovement.VerticalPull,
                            ExerciseMovement.Row
                        )
                    ),
                    Pair(
                        MuscleGroupsSimple.Biceps,
                        listOf(ExerciseMovement.BicepsCurl, ExerciseMovement.BicepsCurl)
                    )
                ),
                name = "Pull-Day"
            ),
            WorkoutBluePrint(
                UID = 2, trainedMuscles = listOf(
                    Pair(
                        MuscleGroupsSimple.Legs,
                        listOf(
                            ExerciseMovement.LegPress,
                            ExerciseMovement.LegPress,
                            ExerciseMovement.LegExtension
                        )
                    ),
                    Pair(
                        MuscleGroupsSimple.Abs,
                        listOf(
                            ExerciseMovement.AbCrunch,
                            ExerciseMovement.AbLegRaise,
                        )
                    )
                ), name = "Leg-Day"
            )
        )
    )
}

