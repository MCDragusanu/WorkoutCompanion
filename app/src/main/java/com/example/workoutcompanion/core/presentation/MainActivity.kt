package com.example.workoutcompanion.core.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.compose.WorkoutCompanionTheme

import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.presentation.app_state.AppStateManager
import com.example.workoutcompanion.core.presentation.app_state.WorkoutCompanionAppState
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.on_board.EntryActivitiy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    @Testing
    lateinit var appStateManager :AppStateManager

    private var userUid : String = guestProfile.uid
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        userUid = intent.getStringExtra("USER_UID") ?: guestProfile.uid
        lifecycleScope.launch {

        }


        setContent {
            WorkoutCompanionTheme {
                var appState by remember { mutableStateOf<WorkoutCompanionAppState?>(null) }
                rememberCoroutineScope().launch {
                    appState = appStateManager.getAppState(userUid)
                }

                MainNavigation.MainNavigation(
                    appState = appState ,
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