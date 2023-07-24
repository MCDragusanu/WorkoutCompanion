package com.example.workoutcompanion.core.presentation.main_navigations

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.workoutcompanion.core.presentation.main_navigations.screens.database.DatabaseScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.home.HomeScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.ProfileScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts.WorkoutScreen

enum class BottomNavItem(val title:String , val route:String , val icon:ImageVector) {

    Home("Home" , HomeScreen.route , Icons.Filled.Home) ,
    Database("Database" , DatabaseScreen.route, Icons.Filled.Archive) ,
    Profile("Profile" , ProfileScreen.route , Icons.Filled.ManageAccounts) ,
    Workouts("Workouts" , WorkoutScreen.route , Icons.Filled.WorkHistory)
}
