package com.example.workoutcompanion.exercise

enum class MuscleGroupsExpanded {
    UpperPec,
    MiddlePec,
    LowerPec,
    FrontDelt,
    LateralDelt,
    RearDelt,
    TricepsLateral,
    TricepsMedial,
    TricepsLong,
    Biceps,
    Traps,
    Rhomboids,
    TeresAndInfra,
    Lats,
    SpinalErectors,
    LowerBack,
    Glutes,
    Quads,
    Hamstrings,
    Calves,
    Abs,
    Obliques,
    Forearms;
    companion object {
        fun parseString(entryString: String): MutableList<Int> {

            var output = mutableListOf<Int>()
            var ant = 0
            entryString.forEach {
                if (it.isDigit()) {
                    ant = ant * 10 + it.digitToInt()
                } else if(it==';') {
                    if (ant in UpperPec.ordinal..Forearms.ordinal) {
                        output.add(ant)
                        ant = 0
                    }
                }
            }

            return output
        }
        fun buildString(vararg items: MuscleGroupsExpanded):String{
            return buildString{
                items.forEach {
                    append(it.ordinal)
                    append(';')
                }
            }
        }

    }



}