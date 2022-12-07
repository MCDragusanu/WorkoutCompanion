package com.example.workoutcompanion.exercise

enum class ExerciseTags {
     Compound,
     Big4;

    companion object {
        fun parseString(entryString: String): MutableList<Int> {

            var output = mutableListOf<Int>()
            var ant = 0
            entryString.forEach {
                if (it.isDigit()) {
                    ant = ant * 10 + it.digitToInt()
                } else if(it==';') {
                    if (ant in Compound.ordinal..Big4.ordinal) {
                        output.add(ant)
                        ant = 0
                    }
                }
            }

            return output
        }
        fun buildString(vararg items: ExerciseTags):String{
            return buildString{
                items.forEach {
                    append(it.ordinal)
                    append(';')
                }
            }
        }

    }
    }
