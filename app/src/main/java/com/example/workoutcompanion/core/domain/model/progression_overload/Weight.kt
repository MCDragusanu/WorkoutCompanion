package com.example.workoutcompanion.workout_designer.progression_overload

class Weight(private val weightInKgs:Double) {

    fun getWeightInKgs() = weightInKgs

    fun getWeightInPounds() = weightInKgs*2.2

}