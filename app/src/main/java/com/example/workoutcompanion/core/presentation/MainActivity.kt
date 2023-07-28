package com.example.workoutcompanion.core.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.core.presentation.main_navigations.database.DatabaseScreenViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.home.HomeViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.profile.ProfileViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard.TrainingProgramViewModel
import com.example.workoutcompanion.on_board.EntryActivitiy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var homeScreenViewModel  : HomeViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var databaseScreenViewModel : DatabaseScreenViewModel
    private lateinit var trainingProgramViewModel : TrainingProgramViewModel
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        val userUid = intent.getStringExtra("USER_UID")?: guestProfile.uid
       Log.d ("Test" , " Uid received in main activity${ userUid }")
        setContent {
            WorkoutCompanionTheme {
                profileViewModel = hiltViewModel<ProfileViewModel>().apply {
                    this.retrieveProfile(userUid)
                }
                homeScreenViewModel = hiltViewModel<HomeViewModel>().apply {
                    this.retriveProfile(userUid )
                }
                databaseScreenViewModel = hiltViewModel<DatabaseScreenViewModel>().apply {

                }
                trainingProgramViewModel = hiltViewModel<TrainingProgramViewModel>().apply {
                    this.retrieveProfile(userUid)
                }

                MainNavigation.MainNavigation(
                    homeViewModel = homeScreenViewModel ,
                    profileViewModel = profileViewModel ,
                    trainingProgramViewModel = trainingProgramViewModel,
                    startOnBoardFlow = {
                           startEntryActivity()
                    }
                )
            }
        }
    }

    fun startEntryActivity(){
        val intent = Intent(this , EntryActivitiy::class.java)
        startActivity(intent)
        this.finish()
    }
}