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
import com.example.workoutcompanion.core.data.workout.workout_session.WorkoutSession
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
    override suspend fun getLatestOneRepMax(uid : Int , userUid : String) :Result<OneRepMax?> {
        return try {
            Result.success(oneRepMaxDao.getLatestRecord(uid , userUid))
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(requestName = "Retrieve Latest Record" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun addWorkoutMetadata(workoutMetadata : WorkoutMetadata):Result<Nothing?> {
        return try {
            workoutMetadataDao.addNewWorkout(workoutMetadata)
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Add Workout" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun addExerciseSlot(slot : ExerciseSlot):Result<Nothing?> {
        return try {
            exerciseSlotDao.addExerciseSlot(slot)
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Add Exercise " ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun addWeek(week : Week):Result<Nothing?> {
        return try {
            weekDao.addWeek(week = week)
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Add new Week" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun addSets(vararg sets : SetSlot):Result<Nothing?> {
        var error :Exception? = null
        CoroutineScope(Dispatchers.IO).launch {
           try {
                sets.onEach {
                    async { setSlotDao.addSet(it) }
                }
            } catch (e : Exception) {
                e.printStackTrace()
               error = WorkoutRepositoryException(requestName = "Add Sets" ,reason = e.localizedMessage?:"Unknown Error")
            }
        }
        return if(error!=null) Result.failure(error!!)
        else Result.success(null)
    }

    override suspend fun getWorkouts(uid : String) : Result<List<WorkoutMetadata>> {
        return try {
            Result.success(workoutMetadataDao.getWorkoutsOfUser(uid))
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(requestName = "Get Workout" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun getSlotsForWorkout(uid : Long) : Result<List<ExerciseSlot>> {
        return try {
            Result.success(exerciseSlotDao.getSlotsForWorkout(uid))
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(requestName = "Retrieve Exercises" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun getWeeksForSlot(slot : ExerciseSlot) : Result<List<Week>> {
        return try {
            Result.success(weekDao.getWeeksForSlotInASCOrder(slot.uid))
        } catch (e : Exception) {
            e.printStackTrace()

            Result.failure(WorkoutRepositoryException(requestName = "Retrieve Records" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun getSetsForWeek(week : Week) : Result<List<SetSlot>> {
        return try {
            Result.success(setSlotDao.getAllSetsForWeek(weekUid = week.uid))
        } catch (e : Exception) {
            e.printStackTrace()
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(requestName = "Retrieve Sets" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun addOneRepMax(oneRepMax : OneRepMax):Result<Nothing?> {
       return try {
            oneRepMaxDao.addOneRepMax(oneRepMax)
            Result.success(null)
        }catch (e:Exception){
            e.printStackTrace()
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(requestName = "Add Record" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun updateWeek(newWeek : Week):Result<Nothing?> {
        return try {
            weekDao.udpateWeek(newWeek)
            Result.success(null)
        }catch (e:Exception){
            e.printStackTrace()

            Result.failure(WorkoutRepositoryException(requestName = "Update Progression" ,reason = e.localizedMessage?:"Unknown Error"))
        }
    }

    override suspend fun updateSet(newSet : SetSlot):Result<Nothing?> {
        return try {
            setSlotDao.updateSetSlot(
                newSet.weightInKgs ,
                newSet.reps ,
                newSet.index ,
                uid = newSet.uid
            )

            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Update Set" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun removeProgression(week : Week):Result<Nothing?> {
        return try {
            weekDao.deleteWeek(week)
            Result.success(null)
        }catch (e:Exception){
            e.printStackTrace()
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(requestName = "Delete Progression" ,reason = e.localizedMessage?:"Unknown Error"))
        }

    }

    override suspend fun getWorkoutByUid(_workoutUid : Long) :Result<WorkoutMetadata?> {

        return try {

            Result.success(workoutMetadataDao.getWorkoutByUid(_workoutUid))
        } catch (e : Exception) {
            e.printStackTrace()
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Get Workout" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun deleteExerciseSlot(slot : ExerciseSlot):Result<Nothing?> {
        return try{
            weekDao.getWeeksForSlotInASCOrder(slot.uid).onEach {
                setSlotDao.deleteAllSetsForWeek(it.uid)
                weekDao.deleteWeek(it)
            }
            exerciseSlotDao.deleteSlot(slot)
            Result.success(null)
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(WorkoutRepositoryException(
                requestName = "Delete Exercise" ,
                reason = e.localizedMessage ?: "Unknown Error"
            ))
        }
    }

    override suspend fun updateMetadata(value : WorkoutMetadata):Result<Nothing?> {

        return try {
            workoutMetadataDao.updateWorkout(value)
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Update Workout" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun removeSet(set : SetSlot):Result<Nothing?> {

        return try {
            setSlotDao.deleteSet(set)
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Remove Set" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun getTrainingParameters(userUid : String) : Result<TrainingParameters?> {
        return try{
            RetrieveTrainingParameters().exercute(
                userUid ,
                trainingParametersDao ,
                progressionSchemaDao
            )
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Get Training Parameters" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun createInitialParameters(uid : String):Result<TrainingParameters> {
        return try {
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
                difficultyCoeff = ExerciseProgressionSchema.primaryCompoundSchema.difficultyCoeff,
                workingSetRestTimeInSeconds = ExerciseProgressionSchema.primaryCompoundSchema.workingSetRestTimeInSeconds,
                warmUpSetRestTimeInSeconds = ExerciseProgressionSchema.primaryCompoundSchema.warmUpSetRestTimeInSeconds,
                warmUpSetCount = ExerciseProgressionSchema.primaryCompoundSchema.warmUpSetCount,
                warmUpRepCount = ExerciseProgressionSchema.primaryCompoundSchema.warmUpRepCount,
                startingWeightPercent = ExerciseProgressionSchema.primaryCompoundSchema.startingWeightPercent,
                weightIncrementPercent = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF ,
                repIncreaseRate = ExerciseProgressionSchema.primaryCompoundSchema.repIncreaseRate ,
                smallestWeightIncrementAvailable = ExerciseProgressionSchema.primaryCompoundSchema.smallestWeightIncrementAvailable
            )
            val schema2 = ProgressionSchema(
                repLowerBound = ExerciseProgressionSchema.secondaryCompoundSchema.repRange.first ,
                repUpperBound = ExerciseProgressionSchema.secondaryCompoundSchema.repRange.last ,
                metadataUid = metadataUid ,
                appliedTo = 1 ,
                difficultyCoeff = ExerciseProgressionSchema.secondaryCompoundSchema.difficultyCoeff,
                workingSetRestTimeInSeconds = ExerciseProgressionSchema.secondaryCompoundSchema.workingSetRestTimeInSeconds,
                warmUpSetRestTimeInSeconds = ExerciseProgressionSchema.secondaryCompoundSchema.warmUpSetRestTimeInSeconds,
                warmUpRepCount = ExerciseProgressionSchema.secondaryCompoundSchema.warmUpRepCount,
                warmUpSetCount = ExerciseProgressionSchema.secondaryCompoundSchema.warmUpSetCount,
                startingWeightPercent = ExerciseProgressionSchema.secondaryCompoundSchema.startingWeightPercent,
                weightIncrementPercent = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF ,
                repIncreaseRate = ExerciseProgressionSchema.secondaryCompoundSchema.repIncreaseRate ,
                smallestWeightIncrementAvailable = ExerciseProgressionSchema.secondaryCompoundSchema.smallestWeightIncrementAvailable
            )
            val schema3 = ProgressionSchema(
                repLowerBound = ExerciseProgressionSchema.isolationSchema.repRange.first ,
                repUpperBound = ExerciseProgressionSchema.isolationSchema.repRange.last ,
                metadataUid = metadataUid ,
                appliedTo = 2 ,
                warmUpSetCount = ExerciseProgressionSchema.isolationSchema.warmUpSetCount,
                warmUpRepCount = ExerciseProgressionSchema.isolationSchema.warmUpRepCount,
                workingSetRestTimeInSeconds = ExerciseProgressionSchema.isolationSchema.workingSetRestTimeInSeconds,
                warmUpSetRestTimeInSeconds = ExerciseProgressionSchema.isolationSchema.warmUpSetRestTimeInSeconds,
                difficultyCoeff = ExerciseProgressionSchema.isolationSchema.difficultyCoeff,
                startingWeightPercent = ExerciseProgressionSchema.isolationSchema.startingWeightPercent,
                weightIncrementPercent = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF ,
                repIncreaseRate = ExerciseProgressionSchema.isolationSchema.repIncreaseRate ,
                smallestWeightIncrementAvailable = ExerciseProgressionSchema.isolationSchema.smallestWeightIncrementAvailable
            )

            progressionSchemaDao.addSchema(schema , schema2 , schema3)
            trainingParametersDao.addParameters(metadatada)
            Result.success(
                TrainingParameters(
                    uid = metadataUid ,
                    userUid = uid ,
                    programUid = -1 ,
                    ExerciseProgressionSchema.primaryCompoundSchema ,
                    ExerciseProgressionSchema.secondaryCompoundSchema ,
                    ExerciseProgressionSchema.isolationSchema
                )
            )

        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Create Default Training Parameters" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun updateSchema(
        schema : ExerciseProgressionSchema ,
        parametersMetadata : Long
    ):Result<Nothing?> {

        return try{
            Log.d("Test" , "Schema will be updated ${schema.uid}")
            progressionSchemaDao.updateSchema(
                ProgressionSchema(
                    repLowerBound = schema.repRange.first ,
                    repUpperBound = schema.repRange.last ,
                    metadataUid = parametersMetadata ,
                    appliedTo = schema.appliedTo.ordinal ,
                    repIncreaseRate = schema.repIncreaseRate ,
                    workingSetRestTimeInSeconds = schema.workingSetRestTimeInSeconds,
                    warmUpSetRestTimeInSeconds = schema.warmUpSetRestTimeInSeconds,
                    weightIncrementPercent = schema.weightIncrementCoeff ,
                    warmUpSetCount =schema.warmUpSetCount,
                    startingWeightPercent = schema.startingWeightPercent,
                    warmUpRepCount = schema.warmUpRepCount,
                    difficultyCoeff = schema.difficultyCoeff,
                    smallestWeightIncrementAvailable = schema.smallestWeightIncrementAvailable
                ).apply { uid = schema.uid }
            )
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Update Progression Schema" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun getLatestWeek(uid : Long) : Result<Week> {
       return try{ Result.success(weekDao.getWeeksForSlotInASCOrder(uid).last()) }catch (e:Exception){
           e.printStackTrace()
           Result.failure(
               WorkoutRepositoryException(
                   requestName = "Get Latest Progression" ,
                   reason = e.localizedMessage ?: "Unknown Error"
               )
           )
       }
    }

    override suspend fun addSession(session : WorkoutSession):Result<Nothing?> {
        return try {
            workoutSessionDao.addSession(session)
            Result.success(null)
        } catch (e : Exception) {
            e.printStackTrace()
            Result.failure(
                WorkoutRepositoryException(
                    requestName = "Update Progression Schema" ,
                    reason = e.localizedMessage ?: "Unknown Error"
                )
            )
        }
    }

    override suspend fun getSession(sessionUid : Long) : Result<WorkoutSession?> {
       return try {
           Result.success( workoutSessionDao.getSessionByUid(sessionUid))
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getExerciseSlotByUid(uid : Long) : Result<ExerciseSlot> {
        return try {
            Result.success(exerciseSlotDao.getSlotById(uid))
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getSetByUid(uid : Int) : Result<SetSlot> {
        return try {
            Result.success(setSlotDao.getSetByUid(uid))
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun updateSession(session : WorkoutSession) : Result<Nothing?> {
        return try {
            workoutSessionDao.updateSession(session = session)

            Result.success(null)
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}