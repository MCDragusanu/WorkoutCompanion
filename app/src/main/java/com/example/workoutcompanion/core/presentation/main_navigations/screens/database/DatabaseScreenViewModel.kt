package com.example.workoutcompanion.core.presentation.main_navigations.screens.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepository
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseScreenViewModel @Inject constructor (
    @Testing
    private val repo:ExerciseRepository):ViewModel() {



    private val _presentedList = MutableStateFlow<Map<String , List<Exercise>>>(mapOf())
    val presentedList = _presentedList.asStateFlow()

    private val _selectedUids = MutableStateFlow<List<Int>>(emptyList())
    val selectedUids = _selectedUids.asStateFlow()

    private val _isInSelectMode = MutableStateFlow<Boolean>(false)
    val isInSelectMode = _isInSelectMode.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val collection = retrieveCachedDatabase().map { it.toExercise() }
            val mapped = mapExercisesToMusleGroup(collection)

            _isLoading.update { false }
            _presentedList.update { mapped }
        }
    }

    private fun mapExercisesToMusleGroup(list : List<Exercise>) : Map<String , List<Exercise>> {
        return list.associate { exercise ->
            val primaryMuscleGroup = exercise.movement.primaryMuscleGroups.first().first.name
            Pair(
                primaryMuscleGroup ,
                list.filter { it.movement.primaryMuscleGroups.first().first.name == primaryMuscleGroup })
        }
    }

    private suspend fun retrieveCachedDatabase():List<ExerciseDocument> {

        val result = repo.getCachedDatabase()
        _isLoading.update { false }

        return if (result.isSuccess) {
            result.getOrNull() ?: emptyList()
        } else emptyList()

    }

    fun buildMuscleGroupString(names:Array<String>, exercise : Exercise) : String {
        val indexes = (exercise.movement.primaryMuscleGroups + exercise.movement.secondaryMuscleGroups).map { it.first.ordinal }
        return buildString {
            when{
                indexes.isEmpty()->append("")
                indexes.size == 1->append(names[indexes[0]])
                indexes.size == 2-> {
                    append(names[indexes[0]])
                    append(" & ")
                    append(names[indexes[1]])
                }
               else ->{
                      indexes.onEachIndexed { index, value ->
                          if(index == 0 ){
                              append(names[value])
                          } else if(index != indexes.lastIndex){
                              append(", ${names[value]} ")
                          } else append(" & ${names[value]}")
                      }
                }
            }
        }
    }

    fun onLongClick(uid : Int) {
        if(!_isInSelectMode.value){
            _isInSelectMode.update { true }
            _selectedUids.update { it+uid }
        } else {
            _selectedUids.update {
                if(uid in it ){
                    it - uid
                } else {
                    it + uid
                }
            }
        }

    }

    fun buildWorkout():List<Exercise> {
        val result = mutableListOf<Exercise>()
        _selectedUids.value.onEach { uid ->
            presentedList.value.values.onEach { list ->
               val ex = list.find { it.uid == uid }
                ex?.let { result.add(it) }
            }
        }
        _selectedUids.update { emptyList() }
        return result
    }

    fun clearSelectedExercises() {
         _selectedUids.update { emptyList() }
    }

    fun closeSelectMode() {
        _isInSelectMode.update { false }
    }

}