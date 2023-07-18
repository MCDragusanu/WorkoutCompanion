package com.example.workoutcompanion.exercise_library.data

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workoutcompanion.exercise_database.model.Exercise
import com.example.workoutcompanion.exercise_database.model.ExerciseInstruction
import com.example.workoutcompanion.exercise_database.model.GripStyle
import com.example.workoutcompanion.exercise_database.model.Movement

@Entity("exercise_document_table")
data class ExerciseDocument(
    val exerciseName: String = "Default",
    @PrimaryKey val uid: Int = -1,
    val equipmentList: String = "",
    val primaryMuscleGroups: String = "",
    val secondaryMuscleGroups: String = "",
    val gripStyle: String = "",
    val instructionList: String = "",
    val name: String = "",
    val movementUid: Int = -1,
    val strengthPotentialRating: Float = 0.0f,
    val techniqueCoeff: Float = 0.0f,
    val exerciseCategory: Int = -1,
    val description: String = "",
    val bodyWeight: Boolean = false,
    val filters: String = "",
    val position: Int = 0,
    val overloadCoeff: Float = 0.0f,
    val rangeOfMotion: Float = 0.0f,
    val sfr_Rating: Float = 0.0f,
    val type: Int = 0
) {
    fun toExercise(): Exercise {
        val equipmentList =  try {
          buildList<String> {
               if (this@ExerciseDocument.equipmentList.isNotBlank()) this@ExerciseDocument.equipmentList.split(
                   "/"
               )
               else emptyList()
           }
       }catch (e:Exception){
           e.printStackTrace()
           Log.d("Test" , "Failed to convert equipment List  for exercise ${this.exerciseName}")
            emptyList()
       }
        val gripStyle = try{
            val gripContent = gripStyle.split("/").filter { it.isNotBlank() }
            if (gripStyle.isBlank() || gripContent.size != 2) GripStyle(
                "Normal Width",
                "Normal"
            ) else GripStyle(gripContent.first(), gripContent.last())
        }catch (e:Exception) {
            e.printStackTrace()
            Log.d("Test", "Failed to convert Grip Style  for exercise ${this.exerciseName}")
            GripStyle(
                "Normal Width",
                "Normal"
            )
        }
        val filters = try {
            filters.split("/").filter { it.isNotBlank() }.map { it.toInt() }
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("Test", "Failed to convert Filters  for exercise ${this.exerciseName}")
            emptyList()
        }

        val exerciseCategory = try{
            Exercise.Companion.ExerciseCategory.values()[exerciseCategory]
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("Test" , "Failed to convert ExerciseCategory  for exercise ${this.exerciseName}")
            Exercise.Companion.ExerciseCategory.values()[1]
        }
        val movement = try {
            Movement.values()[movementUid]
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("Test" , "failed to convert movement  for exercise ${this.exerciseName}")
            Movement.values().first()
        }

        val instructions = try {
            buildList {
                if(instructionList.isNotBlank()) {
                    instructionList.split("/").filter { it.isNotBlank() }.onEach { instructionString ->
                        val fields = instructionString.split("-").filter { it.isNotBlank() }
                        val instruction = ExerciseInstruction(
                            uid = fields[0].toInt(),
                            exerciseUid = fields[1].toInt(),
                            index = fields[3].toInt(),
                            stage = fields[2],
                            instruction = fields[4]
                        )
                        this.add(instruction)
                    }
                }else emptyList()
            }
        }catch (e:Exception){
           e.printStackTrace()
           Log.d("Test" , "Failed to convert instruction  for exercise ${this.exerciseName}")
            emptyList()
        }



        return Exercise(
            uid = uid,
            exerciseName = exerciseName,
            isBodyWeight = bodyWeight,
            position = position,
            overloadCoeff = overloadCoeff,
            rangeOfMotion = rangeOfMotion,
            strengthPotentialRating = strengthPotentialRating,
            techniqueCoeff = techniqueCoeff,
            sfr_rating = sfr_Rating,
            equipmentList = equipmentList,
            gripStyle = gripStyle,
            filters = filters,
            exerciseCategory =exerciseCategory,
            movement = movement,
            instructionList =instructions,
            )
    }
}
