package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.domain.model.workout.Workout
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

object WorkoutScreen:MainNavigation.Screens("Workout Screens") {
    @Composable
    operator fun invoke(viewModel : WorkoutViewModel , onNavigateToExerciseDatabase:()->Unit) {


        Scaffold() {
            Column() {
                Spacer(modifier = Modifier.size(24.dp))
                 Spacer(modifier = Modifier.size(24.dp))
                WorkoutList(workouts = viewModel.workouts) {
                    viewModel.onCreateWorkout()
                    onNavigateToExerciseDatabase()
                }
            }
        }
    }
    private @Composable
    fun WorkoutList(workouts:Flow<List<Workout>>, onCreateWorkout : () ->Unit) {
        val workouts by workouts.collectAsState(initial = emptyList())
        Column(){
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp , Alignment.Start) , verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.workout_tracking_icon) ,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp)
                )
                Text(text = "My\nWorkouts" , style = Typography.headlineSmall)
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2) ,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                items(workouts , key = {
                    it.metadata.uid
                }) { workout ->
                    WorkoutCard(
                        workout = workout ,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(4.dp)
                    )
                }
                if (workouts.size <= 6) {
                    item {
                        AddWorkoutButton(
                            modifier = Modifier
                                .wrapContentSize()
                                .aspectRatio(1f , true) , onClick = onCreateWorkout
                        )
                    }
                }
            }
        }
    }



    private @Composable
    fun AddWorkoutButton( modifier : Modifier, onClick : () -> Unit ) {
        Card(modifier = modifier.clickable { onClick() } , ) {
            Column(
                modifier = Modifier.fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.spacedBy(12.dp , Alignment.CenterVertically)
            ) {
                Surface(modifier = Modifier.wrapContentHeight() , shape = CircleShape) {
                    Icon(
                        imageVector = Icons.Filled.PlusOne ,
                        contentDescription = null ,
                        modifier = Modifier
                            .size(100.dp)
                            .aspectRatio(1f)
                    )
                }
                Text(text = "Create a new Workout" , style = Typography.labelLarge , textAlign = TextAlign.Center)
            }
        }
    }

    @Composable
    fun WorkoutCard(workout : Workout , modifier : Modifier) {
        Card(
            modifier ,
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer ,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.CenterVertically)
            ) {
                Text(text = workout.metadata.workoutName , style = Typography.labelLarge)
                workout.exerciseList.onEach { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() ,
                        verticalAlignment = Alignment.CenterVertically ,
                        horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start)
                    ) {
                        Surface(
                            modifier = Modifier.size(15.dp) ,
                            color = MaterialTheme.colorScheme.surface ,
                            shape = CircleShape
                        ) {}
                        Text(text = exercise.exerciseName)
                    }
                }
            }
        }
    }
}