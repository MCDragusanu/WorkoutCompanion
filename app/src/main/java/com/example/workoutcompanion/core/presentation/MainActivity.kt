package com.example.workoutcompanion.core.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.on_board.EntryActivitiy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private var userUid : String = guestProfile.uid
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        userUid = intent.getStringExtra("USER_UID") ?: guestProfile.uid



        setContent {
            WorkoutCompanionTheme {


                MainNavigation.MainNavigation(
                   userUid = userUid,
                    startOnBoardFlow = {
                        startEntryActivity()
                    }
                )
            }
        }
    }

    fun startEntryActivity() {
        val intent = Intent(this , EntryActivitiy::class.java)
        startActivity(intent)
        this.finish()
    }


}