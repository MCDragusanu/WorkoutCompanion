package com.example.workoutcompanion.workout_designer.progression_overload

import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.data.workout.week.Week
import kotlin.math.*

class ProgressionOverloadManager {


    fun estimate1RepMaxInKgs(weightInKgs:Double , reps:Int) : Double {
        return weightInKgs / (1.0278 - (0.0278 * reps))
    }


    private fun roundToMultiple(multiple : Double , value : Double) : Double {
        return (value / multiple).roundToInt() * multiple
    }

    private fun recalculate(
        previousSolution : Week ,
        progressionSchema : ExerciseProgressionSchema ,


        ) : Week {


        // go down to the lower bound
        val reps = progressionSchema.repRange.first

        //increase the weight by the growth coefficient
        val newWeight =
            previousSolution.weightInKgs * (1 + progressionSchema.weightIncrementCoeff)

        //round to the weight that is a multiple of the increment
        val weight = roundToMultiple(
            progressionSchema.smallestWeightIncrementAvailable ,
            newWeight
        )

        return previousSolution.copy(reps = reps , weightInKgs = weight , index = previousSolution.index+1)
    }

    fun generateSolution(
        previousWeek : Week ,
        progressionSchema : ExerciseProgressionSchema ,
    ) : Week {

        //if the new reps are in the range just increase by the rate
        return if (previousWeek.reps + progressionSchema.repIncreaseRate in progressionSchema.repRange) {
            previousWeek.copy(reps = previousWeek.reps + progressionSchema.repIncreaseRate , index = previousWeek.index+1)
        }
        //recalculate new solution
        else recalculate(
            previousWeek ,
            progressionSchema
        )

    }

    //using a numerical approximation of the relationship between the rep and weight percent
    fun repToPercent(rep : Int) : Double {
        val numerator = 2.0
        return (39.10146 + 65.3282 / numerator.pow(rep / 12.07655)) * 0.01
    }

    //the inverse function to calculate the rep
    fun percentToRep(percent : Float) : Int {
        return (12.07655 * (log2(65.3282) - log2(percent - 39.10146))).toInt()
    }

    private fun convertStartingConditionToPlan(
        setCount : Int ,
        startingCondition : Week ,
        schema : ExerciseProgressionSchema
    ) : Week {
        val oneRepMax = estimate1RepMaxInKgs(startingCondition.weightInKgs ,startingCondition.reps)
        val reps = schema.repRange.first
        val weight = roundToMultiple(
            schema.smallestWeightIncrementAvailable ,
            oneRepMax * repToPercent(reps)
        )
        return startingCondition.copy(sets = setCount , reps = reps , weightInKgs = weight)
    }


    fun findStartingPoint(
        uid : Long ,
        exerciseSlotUid : Long ,
        oneRepMaxWeight:Double ,
        desiredNumberOfSets : Int ,
        schema : ExerciseProgressionSchema
    ) : Week {
        return convertStartingConditionToPlan(
            desiredNumberOfSets ,
            Week(
                uid = uid ,
                exerciseSlotUid = exerciseSlotUid ,
                index = 0,
                1 ,
                1 ,
                weightInKgs = oneRepMaxWeight
            ) ,
            schema
        )
    }
}