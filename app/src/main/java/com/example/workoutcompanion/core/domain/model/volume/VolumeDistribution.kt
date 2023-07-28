package com.example.workoutcompanion.workout_designer.volume

import com.example.workoutcompanion.core.domain.model.exercise.MuscleGroup


class VolumeDistribution(val chestVolume:Int,
                         val tricepsVolume:Int,
                         val frontDeltsVolume:Int,
                         val sideDeltsVolume:Int,
                         val rearDeltsVolume:Int,
                         val upperBackVolume:Int,
                         val latsVolume:Int,
                         val bicepsVolume:Int,
                         val quadsVolume:Int,
                         val hamstringsVolume:Int,
                         val glutesVolume:Int,
                         val calvesVolume:Int,
                         val absVolume:Int) {

    fun toMap() = mapOf(
        Pair(MuscleGroup.Chest.name, chestVolume),
        Pair(MuscleGroup.Triceps.name, tricepsVolume),
        Pair(MuscleGroup.FrontDelts.name, frontDeltsVolume),
        Pair(MuscleGroup.SideDelts.name, sideDeltsVolume),
        Pair(MuscleGroup.RearDelts.name, rearDeltsVolume),
        Pair(MuscleGroup.Lats.name , latsVolume),
        Pair(MuscleGroup.UpperBack.name, upperBackVolume),
        Pair(MuscleGroup.Biceps.name, bicepsVolume),
        Pair(MuscleGroup.Quads.name, quadsVolume),
        Pair(MuscleGroup.Hamstrings.name, hamstringsVolume),
        Pair(MuscleGroup.Glutes.name, glutesVolume),
        Pair(MuscleGroup.Calves.name, calvesVolume),
        Pair(MuscleGroup.Abs.name, absVolume)
    )

    companion object {
        val Beginner = VolumeDistribution(
            chestVolume = 12,
            tricepsVolume = 8,
            frontDeltsVolume = 4,
            rearDeltsVolume = 4,
            sideDeltsVolume = 4,
            upperBackVolume =8,
            latsVolume = 8,
            bicepsVolume = 8,
            quadsVolume = 8,
            hamstringsVolume = 4,
            glutesVolume = 4,
            calvesVolume = 4,
            absVolume = 4
        )
        val Intermediate = VolumeDistribution(
            chestVolume = 16,
            tricepsVolume = 12,
            frontDeltsVolume = 8,
            rearDeltsVolume = 8,
            sideDeltsVolume = 8,
            upperBackVolume =12,
            latsVolume = 8,
            bicepsVolume = 12,
            quadsVolume = 12,
            hamstringsVolume = 8,
            glutesVolume = 4,
            calvesVolume = 8,
            absVolume = 8
        )
        val Advanced = VolumeDistribution(
            chestVolume = 20,
            tricepsVolume = 12,
            frontDeltsVolume = 12,
            rearDeltsVolume = 8,
            sideDeltsVolume = 8,
            upperBackVolume =12,
            latsVolume = 12,
            bicepsVolume = 12,
            quadsVolume = 16,
            hamstringsVolume = 8,
            glutesVolume = 4,
            calvesVolume = 8,
            absVolume = 8
        )
    }
}