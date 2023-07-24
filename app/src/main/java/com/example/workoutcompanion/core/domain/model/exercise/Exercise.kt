package com.example.workoutcompanion.core.domain.model.exercise

import android.util.Log
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument


open class Exercise(
    val exerciseName: String ,
    val uid: Int ,
    val equipmentList: List<String> ,
    val isBodyWeight: Boolean ,
    val position: Int ,
    val gripStyle: GripStyle ,
    val overloadCoeff: Float ,//coeff that describe how easy you can increase the weight 1 -> very bad 10 -> Mammma Mia
    val techniqueCoeff: Float , // how difficult / experience requires is the exercise
    val sfr_rating: Float ,//Stimulus to Fatigue Ratio , describe how effective are these exercise 1-> very bad 10 excellent
    val strengthPotentialRating: Float ,//Describes how much strength carryover you can gain from 1->very bad 10 -> excellent
    val rangeOfMotion: Float ,/* Describes how much you are using range of motion in your advantage 1->bad 10 -> excellent*/
    val filters:List<Int> ,
    val exerciseCategory: ExerciseCategory ,
    val movement: Movement ,
    val instructionList: List<ExerciseInstruction>
){
    fun calculateScore() = ((this.sfr_rating + this.overloadCoeff + this.rangeOfMotion + this.techniqueCoeff + this.strengthPotentialRating) / 50f)
    fun toDocument(): ExerciseDocument {
        val grip = this.gripStyle.gripType + "/" + this.gripStyle.gripWidth

        val equipment = buildString {
            this@Exercise.equipmentList.onEach {
                append("$it/")
            }
        }

        val instructions = buildString {
            this@Exercise.instructionList.onEach {
                val st = "${it.uid}-${it.exerciseUid}-${it.stage}-${it.index}-${it.instruction}/"
                append(st)
            }
        }

        val primaryMuscleGroups = buildString {
            this@Exercise.movement.primaryMuscleGroups.onEach {
                val st = it.first.name + "-" + it.second.toDouble()+"/"
                append(st)
            }
        }

        val secondyMuscleGroups = buildString {
            this@Exercise.movement.secondaryMuscleGroups.onEach {
                val st = it.first.name + "-" + it.second + "/"
                append(st)
            }
        }
        val filters = buildString {
            this@Exercise.filters.onEach {
                append(it)
                append("/")
            }
        }
        return ExerciseDocument(
            exerciseName = exerciseName,
            uid = uid ,
            equipmentList =  equipment,
            primaryMuscleGroups = primaryMuscleGroups,
            secondaryMuscleGroups = secondyMuscleGroups,
            gripStyle = grip,
            instructionList = instructions,
            name =movement.name,
            movementUid =movement.movementUid,
            strengthPotentialRating = strengthPotentialRating,
            techniqueCoeff = techniqueCoeff,
            exerciseCategory = exerciseCategory.ordinal,
            description = movement.description,
            bodyWeight = isBodyWeight,
            filters = filters,
            position = position,
            overloadCoeff = overloadCoeff,
            rangeOfMotion = rangeOfMotion,
            sfr_Rating = sfr_rating,
            type = movement.type

        )
    }

    fun debugExercise() {
        Log.d("Test", "****************************")
        Log.d("Test", "Exercise Name = " + this.exerciseName)
        Log.d("Test", buildString {
            append("Equipment = ")
            equipmentList.onEach {
                append(it)
                append(" ; ")
            }
        })
        Log.d("Test", buildString {
            append("Filters = ")
            filters.onEach {

                append(it)
                append(" ; ")
            }
        })
        Log.d("Test" , buildString {
            append("Primary Muscle Groups")
            this@Exercise.movement.primaryMuscleGroups.onEach {
                append("(")
                append(it.first.name)
                append(",")
                append(it.second)
                append(")")
            }
        })
        Log.d("Test" , buildString {
            append("Secondary Muscle Groups")
            this@Exercise.movement.secondaryMuscleGroups.onEach {
                append("(")
                append(it.first.name)
                append(",")
                append(it.second)
                append(")")
            }
        })
        Log.d("Test", "Instructions = " + buildString {
            append("(")
            instructionList.onEach {
                append("${it.uid} ,")
                append("${it.exerciseUid} , ")
                append("${it.index} , ")
                append("${it.stage} , ")
                append("${it.instruction})")
            }
        })
    }
    companion object{
        enum class ExerciseCategory{
            PrimaryCompound,
            SecondaryCompound,
            Isolation
        }
    }
}






