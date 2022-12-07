package com.example.workoutcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutcompanion.graphs.EntryGraph
import com.example.workoutcompanion.graphs.EntryGraphViewModel
import com.example.workoutcompanion.room.AccountRepositoryImpl
import com.example.workoutcompanion.room.AccountDatabase
import com.example.workoutcompanion.screens.entry.create_account_account.CreateAccountViewModel

import com.example.workoutcompanion.theme.WorkoutCompanionTheme
import com.example.workoutcompanion.workout.TrainingExperience2

class EntryActivity : ComponentActivity() {
    val viewModel:CreateAccountViewModel = CreateAccountViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        val repo =AccountRepositoryImpl( AccountDatabase.getInstance(this).accountDao)
       // viewModel.setRepository(repo)
        test()
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutCompanionTheme {
                EntryGraph(repo, createAccountViewModel = CreateAccountViewModel() , entryGraphViewModel = EntryGraphViewModel())
            }
        }
    }
    fun test(){
        TrainingExperience2.Beginner.logValues()
        TrainingExperience2.Intermiediate.logValues()
        TrainingExperience2.Advanced.logValues()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkoutCompanionTheme {
        Greeting("Android")
    }
}