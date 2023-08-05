package com.example.workoutcompanion.core.data.workout

import android.util.Log
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlotDao
import com.example.workoutcompanion.core.data.workout.one_rep_max.OneRepMax
import com.example.workoutcompanion.core.data.workout.one_rep_max.OneRepMaxDao
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlotDao
import com.example.workoutcompanion.core.data.workout.training_parameters.ProgressionSchema
import com.example.workoutcompanion.core.data.workout.training_parameters.ProgressionSchemaDao
import com.example.workoutcompanion.core.data.workout.training_parameters.TrainingParametersDao
import com.example.workoutcompanion.core.data.workout.training_parameters.TrainingParametersMetadata
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.week.WeekDao
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadataDao
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSessionDao
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.domain.use_cases.RetrieveTrainingParameters
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WorkoutRepositoryImpl (private val exerciseSlotDao : ExerciseSlotDao ,
                             private val oneRepMaxDao : OneRepMaxDao ,
                             private val setSlotDao : SetSlotDao ,
                             private val weekDao : WeekDao ,
                             private val workoutMetadataDao : WorkoutMetadataDao ,
                             private val workoutSessionDao : WorkoutSessionDao,
                             private val progressionSchemaDao : ProgressionSchemaDao,
                             private val trainingParametersDao : TrainingParametersDao,
                             ):WorkoutRepository {
    override suspend fun getLatestOneRepMax(uid : Int , userUid : String) : OneRepMax? {
        return try {
            oneRepMaxDao.getLatestRecord(uid , userUid)
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun addWorkoutMetadata(workoutMetadata : WorkoutMetadata) {
        try {
            workoutMetadataDao.addNewWorkout(workoutMetadata)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addExerciseSlot(slot : ExerciseSlot) {
        try {
            exerciseSlotDao.addExerciseSlot(slot)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addWeek(week : Week) {
        try {
            weekDao.addWeek(week = week)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addSets(vararg sets : SetSlot) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                sets.onEach {
                    async { setSlotDao.addSet(it) }
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getWorkouts(uid : String) : List<WorkoutMetadata> {
        return try {
            workoutMetadataDao.getWorkoutsOfUser(uid)
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getSlotsForWorkout(uid : Long) : List<ExerciseSlot> {
        return try {
            exerciseSlotDao.getSlotsForWorkout(uid)
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getWeeksForSlot(slot : ExerciseSlot) : List<Week> {
        return try {
            weekDao.getWeeksForSlotInASCOrder(slot.uid)
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getSetsForWeek(week : Week) : List<SetSlot> {
        return try {
            setSlotDao.getAllSetsForWeek(weekUid = week.uid)
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun addOneRepMax(oneRepMax : OneRepMax) {
        oneRepMaxDao.addOneRepMax(oneRepMax)
    }

    override suspend fun updateWeek(newWeek : Week) {
        Log.d("Test" , "week will be updated")
        weekDao.udpateWeek(newWeek)
    }

    override suspend fun updateSet(newSet : SetSlot) {
        Log.d("Test" , "set ${newSet.uid} will be updated")
        try {
            setSlotDao.updateSetSlot(
                newSet.weightInKgs ,
                newSet.reps ,
                newSet.index ,
                uid = newSet.uid
            )
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun removeProgression(week : Week) {
        weekDao.deleteWeek(week = week)
    }

    override suspend fun getWorkoutByUid(_workoutUid : Long) : WorkoutMetadata? {
        return try {
            workoutMetadataDao.getWorkoutByUid(_workoutUid)
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun deleteExerciseSlot(slot : ExerciseSlot) {
        weekDao.getWeeksForSlotInASCOrder(slot.uid).onEach {
            setSlotDao.deleteAllSetsForWeek(it.uid)
            weekDao.deleteWeek(it)
        }
        exerciseSlotDao.deleteSlot(slot)
    }

    override suspend fun updateMetadata(value : WorkoutMetadata) {
        workoutMetadataDao.updateWorkout(value)
    }

    override suspend fun removeSet(set : SetSlot) {
        setSlotDao.deleteSet(set)
    }

    override suspend fun getTrainingParameters(userUid : String) : Result<TrainingParameters?> =
        RetrieveTrainingParameters().exercute(
            userUid ,
            trainingParametersDao ,
            progressionSchemaDao
        )

    override suspend fun createInitialParameters(uid : String) {
        try {
            val metadataUid = System.currentTimeMillis()
            val metadatada = TrainingParametersMetadata(
                uid = metadataUid ,
                name = "Default Parameters" ,
                description = "Recommended Progression" ,
                ownerUid = uid
            )
            val schema = ProgressionSchema(
                repLowerBound = ExerciseProgressionSchema.primaryCompoundSchema.repRange.first ,
                repUpperBound = ExerciseProgressionSchema.primaryCompoundSchema.repRange.last ,
                metadataUid = metadataUid ,
                appliedTo = 0 ,
                weightIncrementPercent = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF ,
                repIncreaseRate = ExerciseProgressionSchema.primaryCompoundSchema.repIncreaseRate ,
                smallestWeightIncrementAvailable = ExerciseProgressionSchema.primaryCompoundSchema.smallestWeightIncrementAvailable
            )
            val schema2 = ProgressionSchema(
                repLowerBound = ExerciseProgressionSchema.secondaryCompoundSchema.repRange.first ,
                repUpperBound = ExerciseProgressionSchema.secondaryCompoundSchema.repRange.last ,
                metadataUid = metadataUid ,
                appliedTo = 1 ,
                weightIncrementPercent = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF ,
                repIncreaseRate = ExerciseProgressionSchema.secondaryCompoundSchema.repIncreaseRate ,
                smallestWeightIncrementAvailable = ExerciseProgressionSchema.secondaryCompoundSchema.smallestWeightIncrementAvailable
            )
            val schema3 = ProgressionSchema(
                repLowerBound = ExerciseProgressionSchema.isolationSchema.repRange.first ,
                repUpperBound = ExerciseProgressionSchema.isolationSchema.repRange.last ,
                metadataUid = metadataUid ,
                appliedTo = 2 ,
                weightIncrementPercent = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF ,
                repIncreaseRate = ExerciseProgressionSchema.isolationSchema.repIncreaseRate ,
                smallestWeightIncrementAvailable = ExerciseProgressionSchema.isolationSchema.smallestWeightIncrementAvailable
            )

            progressionSchemaDao.addSchema(schema , schema2 , schema3)
            trainingParametersDao.addParameters(metadatada)

        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateSchema(
        schema : ExerciseProgressionSchema ,
        parametersMetadata : Long
    ) {
        Log.d("Test" , "metadataUid = ${parametersMetadata} ,schemaUid = ${schema.uid}")
        progressionSchemaDao.updateSchema(
            ProgressionSchema(
                repLowerBound = schema.repRange.first ,
                repUpperBound = schema.repRange.last ,
                metadataUid = parametersMetadata ,
                appliedTo = schema.appliedTo.ordinal,
                repIncreaseRate = schema.repIncreaseRate,
                weightIncrementPercent = schema.weightIncrementCoeff,
                smallestWeightIncrementAvailable = schema.smallestWeightIncrementAvailable
            ).apply { uid = schema.uid }
        )
    }
}