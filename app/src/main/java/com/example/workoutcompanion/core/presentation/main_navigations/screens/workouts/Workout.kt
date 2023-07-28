package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import android.util.Log
import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata

class Workout(val metadata : WorkoutMetadata){
        private val _map = mutableMapOf<ExerciseSlot , List<Week>>()

        fun addWeek(new:Week , key:ExerciseSlot):Workout{
            this._map[key].let {
                val current = it?: emptyList()
                _map[key] = (current + new).sortedBy { it.index }
            }
            return this
        }

        fun updateWeek(new : Week , key : ExerciseSlot):Workout{
            this._map[key].let {
                val current = it?: emptyList()
                _map[key] = current.replace(new){
                    it.uid == new.uid
                }
            }
            return this
        }

        fun deleteWeek(oldWeek : Week , key : ExerciseSlot):Workout{
            this._map[key].let {
                val current = it?: emptyList()
                _map[key] = current.filter {it.uid !=oldWeek.uid  }.sortedBy { it.index }
            }
            return this
        }

        fun getLatestWeek(key : ExerciseSlot):Week?{
            return if(_map[key] == null || _map[key]!!.isEmpty()) null
            else _map[key]!!.last()
        }

        fun addEntry(key : ExerciseSlot , weeks : List<Week> = emptyList()):Workout{
            this._map[key] = weeks
            return this
        }
        fun removeEntry(key : ExerciseSlot):Workout{
            val result = this._map.remove(key)
            if(result == null){
                Log.d("Test" , "Key ${key.uid} is not inside")
            }
            return this
        }

        fun getLatestWeeks():List<Week>{
            val result = mutableListOf<Week>()
            _map.onEach {
                if(it.value.isNotEmpty()){
                    result.add(it.value.last())
                }
            }
            return result
        }
        fun getEntries():List<Pair<ExerciseSlot , List<Week>>> = _map.toList()


    }