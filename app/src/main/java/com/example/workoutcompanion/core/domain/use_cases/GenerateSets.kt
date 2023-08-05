package com.example.workoutcompanion.core.domain.use_cases

import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week

class GenerateSets {

     fun execute(week : Week):List<SetSlot>{
         val result = mutableListOf<SetSlot>()
         repeat(week.sets){
             val set = SetSlot(
                 weightInKgs = week.weightInKgs ,
                 reps = week.reps ,
                 weekUid = week.uid ,
                 index = it
             )
             result+=set
         }
         return result
     }
}