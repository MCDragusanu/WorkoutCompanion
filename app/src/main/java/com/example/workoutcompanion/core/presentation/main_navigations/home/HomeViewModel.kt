package com.example.workoutcompanion.core.presentation.main_navigations.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.ComponentType
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@ComponentType(false) private val repository : ProfileRepository , @ComponentType(false) private val appStateManager : AppStateManager):ViewModel(){

    //TODO handle the logic to see if the user has a account but no profile in db


    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()
    private var appState: WorkoutCompanionAppState? = null
    fun retrieveAppState(userUid:String){
        viewModelScope.launch(Dispatchers.IO){
            appStateManager.getAppState(userUid).collectLatest{ newState->
                if(newState == null ){
                    Log.d("Test" , "Home ViewModel::Current App State is null")
                }
                appState = newState
                newState?.let {
                    _profile.update {  newState.userProfile}
                    Log.d("Test" , "Home ViewModel::Current App State has been updated")
                    Log.d("Test" , "Received user = ${it.userProfile.uid}" )
                }
            }
        }
    }




}