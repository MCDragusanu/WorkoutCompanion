package com.example.workoutcompanion.workout_designer.exercise_picker

import android.util.Log
import com.example.workoutcompanion.core.domain.model.exercise.*


//todo Implement a modification to control exercise variety

class ExercisePicker() {
    //mapping each movement to the muscle group it targets
    val movementToMuscleMap = mapOf(
        Pair(
           MuscleGroup.Chest,
            listOf(Movement.ChestPress, Movement.ChestFly, Movement.ChestDips)
        ),
        Pair(
           MuscleGroup.FrontDelts,
            listOf(Movement.ShoulderPress, Movement.ShoulderFrontFly)
        ),
        Pair(
            MuscleGroup.SideDelts,
            listOf(Movement.ShoulderLateralFly)
        ),
        Pair(
           MuscleGroup.RearDelts,
            listOf(Movement.ShoulderRearFly)
        ),
        Pair(
            MuscleGroup.Triceps,
            listOf(Movement.TricepsPushdown, Movement.TricepsExtension)
        ),
        Pair(
            MuscleGroup.UpperBack,
            listOf(Movement.VerticalPull, Movement.Row)
        ),
        Pair(
            MuscleGroup.Lats,
            listOf(Movement.VerticalPull, Movement.Row)
        ),
        Pair(
            MuscleGroup.Quads,
            listOf(Movement.Squat, Movement.Lunge, Movement.LegExtension)
        ),
        Pair(
            MuscleGroup.Glutes,
            listOf(Movement.HipThrusts, Movement.GlutesKickBacks, Movement.Deadlift)
        ),

        Pair(
            MuscleGroup.Hamstrings,
            listOf(Movement.LegCurl)
        ),

        Pair(
            MuscleGroup.Calves,
            listOf(Movement.CalvesRaise)
        ),

        Pair(
            MuscleGroup.Abs,
            listOf(Movement.Crunch, Movement.LegRaise)
        ),

        Pair(
            MuscleGroup.Biceps,
            listOf(Movement.BicepsCurl)
        )
    )

    private fun generateWeightVector(
        exercise: Exercise ,
        exerciseFilters: List<ExerciseFilter>
    ): Pair<Int, List<Float>> {
        val list = mutableListOf<Float>()

        //adding the score of the exercise
        list.add(exercise.calculateScore())

        //building the list of weights with default value of 0
        for (index in ExerciseFilter.values().indices) {
            list.add(0.0f)
        }

        for (filter in exerciseFilters) {
            //checking if the exercise is influenced by the filter
            if (filter.uid in exercise.filters) {
                //checking if it can add the filter to it's matching cell  to avoid IndexOutOfBounds for the last element
                if (filter.uid + 1 in list.indices) list[filter.uid + 1] = filter.weight
                else list.add(filter.weight)
            } else continue
        }
        return Pair(exercise.uid, list)
    }

    fun generateWeightMatrix(
        exerciseList: List<Exercise>, exerciseFilters: List<ExerciseFilter>
    ): List<Pair<Int, List<Float>>> {
        val result = mutableListOf<Pair<Int, List<Float>>>()
        exerciseList.onEach {
            result.add(generateWeightVector(it, exerciseFilters))
        }

        return result
    }

    fun pickExerciseByMuscleGroup(
        exercises: List<Exercise>,
        exerciseFilters: List<ExerciseFilter>,
        exerciseCountList: Map<String, Int>,
    ): List<Exercise> {
        var res = mutableListOf<Exercise>()
        //mapping the movement patterns to the muscles it trains
        val exerciseMap = movementToMuscleMap

        exerciseMap.onEach { pair ->
            //picking only the exercises derived from the movements that hit that muscle group
            val newList = exercises.filter { it.movement.movementUid in pair.value.map { it.movementUid } }

            //matrix containing the weights for each exercise , sorted by the total weight
            val weightMatrix =
                generateWeightMatrix(newList, exerciseFilters).sortedBy { row -> -row.second.sum() }


            //selecting the first N exercises with the highest cost
            //where N = `exerciseCountList[key]` is the amount of exercises for that muscle group
            //if the amount of exercises is greater than the filtered exercises
            //is picked the min of those
            val pickedIds = when {

                exerciseCountList[pair.key.name] == null -> { // No muscle group found , possible error
                    Log.e("Test", "Muscle Group  with key ${pair.key} not found")
                    emptyList()
                }
                exerciseCountList[pair.key.name] in weightMatrix.indices -> {
                    //the amount is less than available exercise
                    val amount = exerciseCountList[pair.key.name]!!
                    //creating  a list of the uids of the picked exercises
                    weightMatrix.subList(0, amount).map { it.first }
                }
                (exerciseCountList[pair.key.name] ?: Int.MAX_VALUE) > weightMatrix.indices.last -> {
                    Log.e("Test", "error caused by ${pair.key}")
                    //the amount is greater than available exercises
                    val amount = weightMatrix.indices.last
                    //creating  a list of the uids of the picked exercises
                    if(amount == -1) {
                        Log.d("Test", "No exercises found for ${pair.key}")
                        emptyList()
                    }
                    else
                    weightMatrix.subList(0, amount).map { it.first }
                }
                else -> {
                    Log.e(
                        "Test",
                        "Index not in 0...${weightMatrix.indices.last} ,current Value = ${exerciseCountList[pair.key.name]}"
                    )
                    emptyList()
                }

            }

            //building the exercise list from the uid list
            val pickedExercise = newList.filter { it.uid in pickedIds }
            res += pickedExercise

        }
        return res
    }
}