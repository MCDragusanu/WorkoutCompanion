package com.example.projectfitness.presentation.WorkoutDesigner

import android.util.Log
import com.example.workoutcompanion.exercise.Exercise
import com.example.workoutcompanion.exercise.ExerciseCollection
import com.example.workoutcompanion.workout.TrainingExperience2
import com.example.workoutcompanion.workout.TrainingSplit


class WorkoutDesigner(val trainingSplit: TrainingSplit = TrainingSplit.PPL,
                      val experienceLevel:TrainingExperience2 = TrainingExperience2.Advanced) {
    fun buildExerciseStack(): Map<Int, MutableList<Exercise>> {
        var map = mapOf<Int, MutableList<Exercise>>()
        ExerciseCollection.apply {

            val list =
                chestExercises + shoulderExercises + tricepsExercises + rowExercises + verticalPullExercises + abCrunchExercises + abLegRaiseExercises + legExtentionExercises + legPressExercises + bicepsCurls
            map = list.associate {
                Pair(
                    it.movementCategory,
                    list.filter { item -> it.movementCategory == item.movementCategory }
                        .sortedBy { ex -> ex.getScore() }.toMutableList()
                )
            }
        }
        Log.d("Test", "stack count = ${map.size}")
        return map
    }

    fun buildWorkout(split: TrainingSplit = this.trainingSplit): Map<String, List<Exercise>> {
        val map = mutableMapOf<String, MutableList<Exercise>>()
        val exerciseStack = buildExerciseStack() //Lista de ex are cheia MuscleGroupSimple

        split.workoutBluePrints.onEach { bluePrint ->
            val exList = mutableListOf<Exercise>()
            bluePrint.trainedMuscles.onEach { pair ->
                //numar de cate ori apare fiecare miscare
                val movementFreq = pair.second.associate { movement ->
                    Pair(
                        movement,
                        pair.second.count { it.ordinal == movement.ordinal })
                }
                //pentru fiecare entry adaug in lista ultimele entry.value exercitii
                // unde entry.value repr de cate ori apare miscarea
                movementFreq.onEach { entry ->
                    val foundEx =
                        exerciseStack[entry.key.ordinal]!!.filter { ex -> ex.movementCategory == entry.key.ordinal && ex.primaryMuscleTag == pair.first.ordinal }
                    //sortez rezultatul astfel incat
                    // primele entry.value ex sa fie cele pe care le cauta
                    val result = foundEx.sortedBy { -it.getScore() }.subList(0, entry.value)
                    //adaug rezultatul
                    exList += result
                }
            }
            map[bluePrint.name] = exList
        }
        return map
    }
}
