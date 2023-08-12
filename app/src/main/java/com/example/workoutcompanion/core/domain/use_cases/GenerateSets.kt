package com.example.workoutcompanion.core.domain.use_cases

import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import kotlin.math.roundToInt

class GenerateSets {

    //TODO use the weight percent to rep count function to find the matching weight
    //TODO better make the generation inside the manager
    //TODO find a way to introduce a difficulty scalar that will allow tweaking the difficulty of the estimation
     fun execute(week : Week , schema : ExerciseProgressionSchema):List<SetSlot>{
         val result = mutableListOf<SetSlot>()
         val startingPercent = schema.startingWeightPercent
         val dW = (100.0 - startingPercent)/schema.warmUpSetCount
         repeat(schema.warmUpSetCount) {
             val set = SetSlot(
                 weightInKgs = roundToMultiple(
                     schema.smallestWeightIncrementAvailable ,
                     week.weightInKgs * (startingPercent*0.01 + it * dW*0.01)
                 ) ,
                 reps = schema.warmUpRepCount ,
                 exerciseSlotUid = week.exerciseSlotUid,
                 weekUid = week.uid ,
                 type = SetSlot.WarmUp ,
                 index = it
             )
             result += set
         }
         repeat(week.sets){
             val set = SetSlot(
                 weightInKgs = week.weightInKgs ,
                 exerciseSlotUid = week.exerciseSlotUid,
                 reps = week.reps ,
                 weekUid = week.uid ,
                 type = SetSlot.WorkingSet,
                 index = schema.warmUpSetCount+it
             )
             result+=set
         }
         return result
     }

    private fun roundToMultiple(multiple : Double , value : Double) : Double {
        return (value / multiple).roundToInt() * multiple
    }
}