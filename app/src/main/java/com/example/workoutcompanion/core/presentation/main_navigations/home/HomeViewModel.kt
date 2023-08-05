package com.example.workoutcompanion.core.presentation.main_navigations.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@Production private val repository : ProfileRepository):ViewModel(){

    //TODO handle the logic to see if the user has a account but no profile in db

    private var _userUid:String? = null
    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    fun retriveProfile(uid:String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProfileFromLocalSource(uid , this).onSuccess {
                Log.d("Test" , ("Home View Model :: ${ it?.uid ?: "No profile received" }" ))
                withContext(Dispatchers.Main) { _profile.emit(it)  }
                Log.d("Test" , "Success is called")
            }.onFailure {
                Log.d("Test" , it.stackTraceToString())
            }
        }
    }



}