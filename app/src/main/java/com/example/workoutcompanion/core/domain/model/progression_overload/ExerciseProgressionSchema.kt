package com.example.workoutcompanion.workout_designer.progression_overload

import com.example.workoutcompanion.core.domain.model.exercise.Exercise


data class ExerciseProgressionSchema(
                                     val trainingParametersUid:Long ,
                                     val repRange:IntRange ,
                                     val appliedTo: Exercise.Companion.ExerciseCategory ,
                                     val weightIncrementCoeff:Double ,
                                     val smallestWeightIncrementAvailable:Double ,
                                     val repIncreaseRate:Int ,
                                     ) {
    var uid : Int = 0

    companion object {
        const val DEFAULT_GROUTH_COEFF = 0.025

        val primaryCompoundSchema = ExerciseProgressionSchema(
            appliedTo = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
            trainingParametersUid = -1 ,
            repRange = (4..8),
            weightIncrementCoeff = DEFAULT_GROUTH_COEFF,
            smallestWeightIncrementAvailable = 2.5,
            repIncreaseRate = 2
        )

        val secondaryCompoundSchema = ExerciseProgressionSchema(
            appliedTo = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
            trainingParametersUid = -1 ,
            repRange = (10..15),
            weightIncrementCoeff = DEFAULT_GROUTH_COEFF,
            smallestWeightIncrementAvailable = 2.5,
            repIncreaseRate = 2
        )

        val isolationSchema = ExerciseProgressionSchema(
            appliedTo = Exercise.Companion.ExerciseCategory.Isolation ,
            trainingParametersUid = -1 ,
            repRange = (10..15),
            weightIncrementCoeff = DEFAULT_GROUTH_COEFF,
            smallestWeightIncrementAvailable = 2.5,
            repIncreaseRate = 2
        )
    }
}