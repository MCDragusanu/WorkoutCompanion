package com.example.workoutcompanion.entry_navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.MainActivity
import com.example.workoutcompanion.workout_designer.WorkoutDesignActivity
import com.example.workoutcompanion.entry_navigation.login.viewmodel.LoginViewModel
import com.example.workoutcompanion.entry_navigation.on_board.view_model.OnBoardViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntryActivitiy : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        lifecycleScope.launch {
            FirebaseAuth.getInstance().currentUser?.let {
              //  startMainActivity(it.uid)
            }
        }
        setContent {
            WorkoutCompanionTheme {// A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(
                        onLoginCompleted = { startMainActivity(it) },
                        startWorkoutDesignActivity = {
                        startWorkoutDesignActivity(it)
                        },
                        startMainActivity = {
                             startMainActivity(it)
                        }
                    )
                }
            }
        }
    }
    private fun startMainActivity(userUid:String){
        val intent = Intent(this , MainActivity::class.java )
        intent.putExtra("USER_UID" , userUid)
        startActivity(intent)
        this.finish()
    }
    private fun startWorkoutDesignActivity(userUid:String){
        val intent = Intent(this , WorkoutDesignActivity::class.java )
        intent.putExtra("USER_UID" , userUid)
        startActivity(intent)
        this.finish()
    }
}

