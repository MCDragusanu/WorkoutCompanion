package com.example.workoutcompanion.exercise

enum class ExerciseGripWidth  {
    Narrow,
    ShoulderWidth,
    Wide,
    KneeWidth,
    Normal;
    companion object{
        fun buildString(vararg items: ExerciseGripWidth):String{
            return buildString{
                items.forEach {
                    append(it.ordinal)
                    append(';')
                }
            }
        }
        fun parseString(entryString: String): MutableList<Int> {
            var output = mutableListOf<Int>()
            var ant = 0
            entryString.forEach {
                if (it.isDigit()) {
                    ant = ant * 10 + it.digitToInt()
                } else if(it==';') {
                    if (ant in Narrow.ordinal..Normal.ordinal) {
                        output.add(ant)
                        ant = 0
                    }
                }
            }

            return output
        }
    }
}