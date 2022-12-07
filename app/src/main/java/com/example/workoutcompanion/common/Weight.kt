package com.example.workoutcompanion.common

class Weight(private var weightInKgs:Float) {

    fun setWeightInKgs(newWeight:Float){
        this.weightInKgs = newWeight
    }
    fun setWeightInPounds(newWeight:Float){
        this.weightInKgs = newWeight/2.2f
    }
    fun toKgs() = weightInKgs
    fun toPounds() = weightInKgs*2.2f
    fun asKgsString() = "$weightInKgs"
    fun asPoundsString() = "${toPounds()}"
}