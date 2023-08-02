package com.example.workoutcompanion.on_board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.common.composables.BarChart
import com.example.workoutcompanion.core.presentation.MainActivity
import com.example.workoutcompanion.on_board.navigation.EntryNavigation
import com.example.workoutcompanion.workout_designer.WorkoutDesignActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntryActivitiy : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
               // FirebaseAuth.getInstance().signOut()
                    FirebaseAuth.getInstance().currentUser?.let {
                    startMainActivity(it.uid)
                }
            }
        }

        setContent {
            WorkoutCompanionTheme {// A surface container using the 'background' color from the theme

               Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EntryNavigation(
                        onLoginCompleted = { startMainActivity(it) },
                        startWorkoutDesignActivity = {
                        startWorkoutDesignActivity(it)
                        },
                        startMainActivity = {
                             startMainActivity(it)
                        }
                    )
                }
                //Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){ BarChart() }
            }
        }
    }
    private fun startMainActivity(userUid:String){
        val intent = Intent(this , MainActivity::class.java )
        Log.d("Test" , "Intent uid = ${userUid}")
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

