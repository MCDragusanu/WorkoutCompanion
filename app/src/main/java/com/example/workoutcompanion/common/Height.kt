package com.example.workoutcompanion.common

class Height(private var heightCms:Float) {

    companion object {
        val Feet = 30.48f
        val Inch = 2.54f
    }

    fun setHeightInCms(newHeight: Float) {
        this.heightCms = newHeight
    }

    fun setHeightInFeetInches(nFeet: Int, nInches: Int) {
        this.heightCms = nFeet * Feet + nInches * Inch
    }

    fun toCms() = heightCms
    fun toFeetAndInches(): Pair<Int, Int> {
        val nFeet = (heightCms / Feet).toInt()
        val nInches = ((heightCms - nFeet * Feet) / Inch).toInt()
        return Pair(nFeet, nInches)
    }

    fun asCmsString() = "${heightCms} Cms"
    fun asPoundsString() = "${toFeetAndInches().first}Ft ${toFeetAndInches().second}In"
}