package com.example.workoutcompanion.core.presentation.main_navigations.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Production

import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@Testing private val repository : ProfileRepository ):ViewModel(){

    //TODO handle the logic to see if the user has a account but no profile in db



    private val _appState = MutableStateFlow<WorkoutCompanionAppState?>(null)
    val appState = _appState.asStateFlow()
    fun setAppState(initial:WorkoutCompanionAppState?){
        Log.d("Bug" , "Set App State from Home Screen Called once")
        _appState.update {initial}
    }




}