package com.example.workoutcompanion.core.presentation.main_navigations.profile

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(@Testing private val userRepository : ProfileRepository):ViewModel() {
    private var _userUid : String? = null
    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    fun retrieveProfile(uid : String) {
        _userUid = uid
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getProfileFromLocalSource(uid , this).onSuccess {
                Log.d("Test" , "Profile Screen :: ${it?.uid?:"No user profile retrieved"}")
                _profile.update { it }
            }.onFailure {
                Log.d("Test" , it.stackTraceToString())
            }
        }
    }
}
