package com.example.workoutcompanion.exercise

enum class MuscleCategory {
    Chest,
    Shoulders,
    Triceps,
    Back,
    Biceps,
    Legs,
    Abs;
    companion object{
         fun getItem(index:Int) = when(index){
             Chest.ordinal-> Chest
             Shoulders.ordinal-> Shoulders
             Triceps.ordinal-> Triceps
             Back.ordinal-> Back
             Biceps.ordinal-> Biceps
             Legs.ordinal-> Legs
             Abs.ordinal-> Abs
             else -> Chest
         }
    }
}
