package com.example.workoutcompanion.workout

import android.util.Log
import com.example.workoutcompanion.exercise.MuscleGroupsSimple

sealed class TrainingExperience(val primaryExSetCount:Int,
                         val secondaryExSetCount:Int,
                         val weeklyPrimarySetCount:Int,
                         val weeklySecondarySetCount:Int,
                         val recommendedRPE:Int,
                         ) {
    object Beginner : TrainingExperience(
        primaryExSetCount = 5,
        secondaryExSetCount = 3,
        weeklyPrimarySetCount = 16,
        weeklySecondarySetCount = 8,
        recommendedRPE = 7
    )
    object Intermediate : TrainingExperience(
        primaryExSetCount = 5,
        secondaryExSetCount = 3,
        weeklyPrimarySetCount = 24,
        weeklySecondarySetCount = 16,
        recommendedRPE = 8
    )
    object Veteran : TrainingExperience(
        primaryExSetCount = 6,
        secondaryExSetCount = 4,
        weeklyPrimarySetCount = 30,
        weeklySecondarySetCount = 20,
        recommendedRPE = 7
    )

}
sealed class TrainingExperience2(level : Int){
    val volumeList:List<WeeklyMuscleGroupVolume>
    val compoundWorkingSetCount:Int
    val isolationWorkingSetCount:Int
    init {
        val newList = mutableListOf<WeeklyMuscleGroupVolume>()
         for(index in MuscleGroupsSimple.Chest.ordinal..MuscleGroupsSimple.Forearms.ordinal){
             val muscleGroup = MuscleGroupsSimple.valueOf(MuscleGroupsSimple.getName(index))

             val volume = if(MuscleGroupsSimple.isPrimary(index)){
                 16+level*8
             } else {
                 12+level*4
             }
             newList.add(WeeklyMuscleGroupVolume(muscleGroup , volume))
         }
        when(level){
            0 -> {
                compoundWorkingSetCount = 4
                isolationWorkingSetCount = 3
            }
            1->{
                compoundWorkingSetCount = 5
                isolationWorkingSetCount = 4
            }
            2->{
                compoundWorkingSetCount = 6
                isolationWorkingSetCount = 4
            } else ->{
                compoundWorkingSetCount = 4
                isolationWorkingSetCount = 3
            }
        }
        volumeList = newList.toList()

    }
    fun logValues()  {
        volumeList.onEach {
            Log.d("Test" , it.muscleGroup.name+" sets = ${it.numberSets}")
        }
        Log.d("Test" , "\n")
    }
    object Beginner:TrainingExperience2(0)
    object Intermiediate:TrainingExperience2(1)
    object Advanced:TrainingExperience2(2)
}