package com.example.workoutcompanion.core.domain.use_cases

import android.util.Log
import com.example.workoutcompanion.core.data.workout.training_parameters.ProgressionSchemaDao
import com.example.workoutcompanion.core.data.workout.training_parameters.TrainingParametersDao
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters

class RetrieveTrainingParameters {

    suspend fun exercute(
        userUid : String ,
        parametersDao : TrainingParametersDao ,
        progressionSchemaDao : ProgressionSchemaDao
    ) : Result<TrainingParameters?> {
        return try {

            val trainingParametersMetadata = parametersDao.getParametersForUser(userUid)
                ?: throw NullPointerException("Metadata not found")
            val schemas = progressionSchemaDao.getSchemas(trainingParametersMetadata.uid).map {
                ExerciseProgressionSchema(
                    trainingParametersUid = trainingParametersMetadata.uid ,
                    repRange = (it.repLowerBound..it.repUpperBound) ,
                    appliedTo = Exercise.Companion.ExerciseCategory.values()[it.appliedTo] ,
                    weightIncrementCoeff = it.weightIncrementPercent ,
                    smallestWeightIncrementAvailable = it.smallestWeightIncrementAvailable ,
                    repIncreaseRate = it.repIncreaseRate,
                    startingWeightPercent = it.startingWeightPercent,
                    warmUpSetCount = it.warmUpSetCount,
                    warmUpRepCount = it.warmUpRepCount,
                    workingSetRestTimeInSeconds =it.workingSetRestTimeInSeconds,
                    warmUpSetRestTimeInSeconds = it.warmUpSetRestTimeInSeconds,
                    difficultyCoeff =it.difficultyCoeff,

                    ).apply {
                    this.uid = it.uid

                }
            }
            val trainingParameters = TrainingParameters(
                uid = trainingParametersMetadata.uid ,
                userUid = userUid ,
                programUid = -1 ,

                primaryCompoundSchema = schemas.first { it.appliedTo.ordinal == Exercise.Companion.ExerciseCategory.PrimaryCompound.ordinal } ,
                secondaryCompoundSchema = schemas.first { it.appliedTo.ordinal == Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal } ,
                isolationSchema = schemas.first { it.appliedTo.ordinal == Exercise.Companion.ExerciseCategory.Isolation.ordinal }
            )

            Result.success(trainingParameters)

        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    fun getDefaultParameters(userUid : String) : TrainingParameters {
        return TrainingParameters(
            uid = System.currentTimeMillis() ,
            userUid = userUid ,
            programUid = -1 ,
            primaryCompoundSchema = ExerciseProgressionSchema.primaryCompoundSchema ,
            secondaryCompoundSchema = ExerciseProgressionSchema.secondaryCompoundSchema ,
            ExerciseProgressionSchema.isolationSchema
        )
    }
}