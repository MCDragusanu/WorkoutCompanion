package com.example.workoutcompanion.core.presentation.main_navigations.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation

object ProfileScreen:MainNavigation.Screens("Profile Screen") {
    @Composable
    operator fun invoke(profileViewModel : ProfileViewModel){
        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
            Text(text = "This is the Profile screen")
        }
    }
}