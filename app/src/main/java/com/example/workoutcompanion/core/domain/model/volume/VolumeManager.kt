package com.example.workoutcompanion.workout_designer.volume

import kotlin.math.roundToInt

class VolumeManager {



    fun calculateExerciseAmount(
        setsPerExercise:Int,
        volumeDistribution: VolumeDistribution,

        ): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        val volumes = volumeDistribution.toMap().toList()
        for (index in volumes.indices){
            if(volumes[index].second == 0 ) {
                //setting the exercise amoount to 0
                map[volumes[index].first] = 0
            } else {
                //how to handle the remainder case

                val exerciseCount = (volumes[index].second * 1.0 / setsPerExercise).roundToInt()
                map[volumes[index].first] = exerciseCount
            }
        }

        return map
    }

}