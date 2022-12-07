package com.example.workoutcompanion.exercise

enum class MuscleGroupsSimple {
    Chest,
    FrontDelts,
    LateralDelts,
    RearDelts,
    Triceps,
    Back,

    Biceps,
    Legs,
    Abs,
    Forearms;
    companion object {
        fun buildString(vararg items: MuscleGroupsSimple):String{
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
                    if (ant in Chest.ordinal..Forearms.ordinal) {
                        output.add(ant)
                        ant = 0
                    }
                }
            }

            return output
        }
        fun isPrimary(index:Int) = when(index){
            Chest.ordinal , Back.ordinal , Legs.ordinal -> true
            else ->false
        }
        fun getName(index: Int) = when(index){
            Chest.ordinal-> Chest.name
            FrontDelts.ordinal-> FrontDelts.name
            LateralDelts.ordinal-> LateralDelts.name
            RearDelts.ordinal-> RearDelts.name
            Triceps.ordinal-> Triceps.name
            Back.ordinal-> Back.name
            Biceps.ordinal-> Biceps.name
            Forearms.ordinal-> Forearms.name
            Legs.ordinal-> Legs.name
            Abs.ordinal-> Abs.name
            else->{"Error"}
        }
    }
}