package com.example.workoutcompanion.core.domain.model.progression_overload

import com.example.workoutcompanion.core.domain.model.exercise.Exercise


data class ExerciseProgressionSchema(
                                     val trainingParametersUid:Long ,
                                     val repRange:IntRange ,
                                     val warmUpSetCount:Int,
                                     val warmUpRepCount:Int,
                                     val startingWeightPercent:Double,
                                     val appliedTo: Exercise.Companion.ExerciseCategory ,
                                     val workingSetRestTimeInSeconds:Int,
                                     val difficultyCoeff:Float,
                                     val warmUpSetRestTimeInSeconds:Int,
                                     val weightIncrementCoeff:Double ,
                                     val smallestWeightIncrementAvailable:Double ,
                                     val repIncreaseRate:Int ,
                                     ) {
    var uid : Int = 0

    companion object {
        const val SMALL_GROWTH_COEFF = 0.015
        const val NORMAL_GROWTH_COEFF = 0.025
        const val BIG_GROWTH_COEFF = 0.05
        val primaryCompoundSchema = ExerciseProgressionSchema(
            appliedTo = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
            trainingParametersUid = -1 ,
            repRange = (4..8),
            warmUpSetCount = 3,
            warmUpRepCount = 15,
            startingWeightPercent = 50.0,
            difficultyCoeff = 1.0f,
            workingSetRestTimeInSeconds = 60,
            warmUpSetRestTimeInSeconds = 120,
            weightIncrementCoeff = SMALL_GROWTH_COEFF ,
            smallestWeightIncrementAvailable = 2.5,
            repIncreaseRate = 2
        )

        val secondaryCompoundSchema = ExerciseProgressionSchema(
            appliedTo = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
            trainingParametersUid = -1 ,
            warmUpSetCount = 1,
            warmUpRepCount = 15,
            startingWeightPercent = 75.0,
            difficultyCoeff = 1.0f,
            workingSetRestTimeInSeconds = 60,
            warmUpSetRestTimeInSeconds = 90,
            repRange = (10..15),
            weightIncrementCoeff = SMALL_GROWTH_COEFF ,
            smallestWeightIncrementAvailable = 2.5,
            repIncreaseRate = 2
        )

        val isolationSchema = ExerciseProgressionSchema(
            appliedTo = Exercise.Companion.ExerciseCategory.Isolation ,
            trainingParametersUid = -1 ,
            warmUpSetCount = 1,
            warmUpRepCount = 10,
            difficultyCoeff = 1.0f,
            workingSetRestTimeInSeconds = 60,
            warmUpSetRestTimeInSeconds = 90,
            startingWeightPercent = 75.0,
            repRange = (10..15),
            weightIncrementCoeff = SMALL_GROWTH_COEFF ,
            smallestWeightIncrementAvailable = 2.5,
            repIncreaseRate = 2
        )
    }
}