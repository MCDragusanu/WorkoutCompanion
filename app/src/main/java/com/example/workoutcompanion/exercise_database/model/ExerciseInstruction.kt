package com.example.workoutcompanion.exercise_database.model

class ExerciseInstruction(val uid:Int ,
                          val exerciseUid:Int,
                          val index:Int,
                          val stage:String,//Setup , Eccentric , Concentric ,
                          val instruction:String,
                          ) {
}