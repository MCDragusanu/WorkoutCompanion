package com.example.workoutcompanion.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Exercise(
    val name:String,
    val description:String,
    val type:Int,//Compound, Isolation
    val muscleCategory:Int,//Chest , Shoulders , etc
    val movementCategory:Int,//Push , Fly ,etc
    val primaryMuscleTag:Int,
    val primaryMusclesMinimal:String,
    val secondaryMusclesMinimal:String,
    val primaryMusclesExpanded:String,
    val secondaryMusclesExpanded:String,
    val coeffOverloading:Float,// 0 = can't be overloaded ,  10 = very easy to overload
    val coeffTechnique:Float, // 0 = very hard , 10 very easy,
    val coeffStrenghtPotential:Float, // 2 = not much carry over , 5 = strenght gains
    val coeffRangeOfMotion:Float,// 2 = weak  , 7 = complete
    val coeffMusclesInvolved:Float,//+1 for every muscle involved,
    val position:Int,  // Incline Decline etc
    val gripWidthList:String,
    val gripTypeList: String,
    val isBodyWeight:Boolean,
    val equipment:String,
    val icon:Int = -1,
    val frontImage:Int = -1,
    val backImage:Int = -1,
){
    @PrimaryKey var uid:Int = -1
    fun getScore() = (coeffStrenghtPotential + coeffTechnique + coeffOverloading + coeffRangeOfMotion + coeffMusclesInvolved)/5f
}
