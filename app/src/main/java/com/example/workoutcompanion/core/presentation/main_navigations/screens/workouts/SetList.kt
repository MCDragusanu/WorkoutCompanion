package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import com.example.workoutcompanion.common.extentions.replace
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot

class SetList {
    private val _map = mutableMapOf<Long , List<SetSlot>>()

    fun addSet(key:Long , setSlot : SetSlot ):SetList{
        this._map[key].let {
            val current = it?: emptyList()
            _map[key] = current + setSlot
        }
        return this
    }
    fun editSet(new:SetSlot , key : Long):SetList{
        this._map[key].let {
            val current = it?: emptyList()
            _map[key] = current.replace(new){
                it.uid == new.uid
            }
        }
        return this
    }
    fun deleteSet(old:SetSlot , key : Long):SetList{
        this._map[key].let {
            val current = it?: emptyList()
            _map[key] = current.filter { it.uid!=old.uid }
        }
        return this
    }
    fun getSets(key:Long):List<SetSlot>{
        return _map[key]?: emptyList()
    }
    fun addNewEntry(key:Long , value:List<SetSlot>):SetList{
        _map[key] = value
        return this
    }

    fun removeEntry(key:Long):SetList{
       _map.remove(key)
        return this
    }

    fun getSet(uid : Int , key : Long):SetSlot? {
       return (_map[key]?: emptyList()).firstOrNull{
           it.uid == uid
       }
    }

}