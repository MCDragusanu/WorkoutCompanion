package com.example.workoutcompanion.exercise

import android.util.Log

enum class ExerciseEquipment {
    Barbell,
    Dumbbells,
    EzBar,
TrapBar,
Bench,
    Seat,
    PullUpBar,
    PullUpMachine,
    SquatRack,
    SmithMachine,
    DipChair,
    PeckDeck,
    CablePulley,
    ChestMachine,
    ShoulderMachine,
    TricepsMachine,
    RowMachine,
    LatMachine,
    BicepsMachine,
    LegMachine,
    AbsMachine;
    companion object {
        fun parseString(entryString: String): MutableList<Int> {

            var output = mutableListOf<Int>()
            var ant = 0
            entryString.forEach {
                if (it.isDigit()) {
                    ant = ant * 10 + it.digitToInt()
                } else if(it==';') {
                    if (ant in Barbell.ordinal..AbsMachine.ordinal) {
                        output.add(ant)
                        ant = 0
                    }
                }
            }

            return output
        }
        fun buildString(vararg items: ExerciseEquipment):String{
            return buildString{
                items.forEach {
                    append(it.ordinal)
                    append(';')
                }
            }
        }

    }
}