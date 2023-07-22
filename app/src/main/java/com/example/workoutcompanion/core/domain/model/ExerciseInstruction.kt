package com.example.workoutcompanion.core.domain.model

class ExerciseInstruction(val uid:Int ,
                          val exerciseUid:Int,
                          val index:Int,
                          val stage:String,//Setup , Eccentric , Concentric ,
                          val instruction:String,
                          ) {
}