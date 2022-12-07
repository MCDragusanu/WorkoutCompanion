package com.example.workoutcompanion.exercise

enum class ExerciseMovement {
    ChestPress,
    ChestFly,
    ChestDips,
    ShoulderPress,
    ShoulderRaise,
    ShoulderFly,
    Row,
    Hinge,
    VerticalPull,
    HorizontalPull,
    LegPress,
    LegCur,
    LegExtension,
    CalvesRaise,
    BicepsCurl,
    TricepsExtension,
    TricepsPushDown,
    AbCrunch,
    AbLegRaise;

   operator fun plus( other:Int):String{
       val out =  buildString {
           append(this@ExerciseMovement.ordinal)
           append(';')
           append(other)
           append(';')
       }

       return out
   }

    companion object {
        fun parseString(entryString: String): MutableList<Int> {
            var output = mutableListOf<Int>()
            var ant = 0
            entryString.forEach {
                if (it.isDigit()) {
                    ant = ant * 10 + it.digitToInt()
                } else if(it==';') {
                    if (ant in (ChestPress).ordinal..(AbLegRaise).ordinal) {
                        output.add(ant)
                        ant = 0
                    }
                }
            }

            return output
        }
        fun buildString(vararg items: ExerciseMovement):String{
            return buildString{
                   items.forEach {
                       append(it.ordinal)
                       append(';')
                   }
            }
        }
    }
}