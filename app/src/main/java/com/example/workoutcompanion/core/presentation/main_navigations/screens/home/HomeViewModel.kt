package com.example.workoutcompanion.core.presentation.main_navigations.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@Testing private val repository : ProfileRepositoryImpl):ViewModel(){

    //TODO handle the logic to see if the user has a account but no profile in db
    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            val profile = repository.getCurrentUser(this)
            if(profile.isFailure){
                Log.d("Test" , (profile.exceptionOrNull()?:Exception("Unknown exception has occured while retrieving your profile")).stackTraceToString())
                return@launch
            }
            _profile.update { profile.getOrNull() }
        }
    }


}