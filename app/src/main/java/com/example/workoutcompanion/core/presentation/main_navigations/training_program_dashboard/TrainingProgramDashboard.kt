package com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import com.example.workoutcompanion.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import java.lang.Math.ceil

object TrainingProgramDashboard:MainNavigation.Screens("Training Program Dashboard") {
    @Composable
    operator fun invoke(
        viewModel : TrainingProgramViewModel ,
        onNavigateToExerciseDatabase : () -> Unit ,
        navigateToWorkoutScreen:(Long , String)->Unit ,
        onShowNavBar : () -> Unit ,
        onHideNavBar : () -> Unit
    ) {

        val workouts by viewModel.workouts.collectAsState()
        Scaffold(
            modifier = Modifier.fillMaxSize() ,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item{
                    Headline(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }
                item{
                    WorkoutList(modifier = Modifier
                        .fillMaxWidth()
                        .height((kotlin.math.ceil(workouts.size / 2.0) * 80).dp) , workouts = workouts , createNewWorkout = {
                        onNavigateToExerciseDatabase()
                    } , onWorkoutClicked = { navigateToWorkoutScreen(it.uid , it.ownerUid) })
                }
            }
        }
    }

    @Composable
    fun WorkoutList(modifier : Modifier , workouts:List<WorkoutMetadata> , createNewWorkout:()->Unit , onWorkoutClicked:(WorkoutMetadata)->Unit) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "My Workouts" , style = Typography.headlineSmall)
                IconButton(onClick = createNewWorkout) {
                    Icon(
                        imageVector = Icons.Filled.Add ,
                        contentDescription = null ,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            LazyVerticalStaggeredGrid(
                modifier = modifier ,
                userScrollEnabled = false ,
                columns = StaggeredGridCells.Fixed(2) ,
                horizontalArrangement = Arrangement.spacedBy(4.dp) ,
                verticalItemSpacing = 4.dp
            ) {
                items(workouts , key = { it.uid }) {
                    WorkoutCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp) , workoutMetadata = it ,
                        workoutClicked = onWorkoutClicked
                    )
                }

            }
        }
    }

    private @Composable
    fun WorkoutCard( modifier : Modifier, workoutMetadata : WorkoutMetadata , workoutClicked:(WorkoutMetadata)->Unit) {
        Card(
            Modifier
                .wrapContentWidth(Alignment.Start)
                .wrapContentHeight(Alignment.Top)
                .clickable {
                    workoutClicked(workoutMetadata)
                }
                .then(modifier) ,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer , contentColor = MaterialTheme.colorScheme.onSecondaryContainer) ,
            shape = cardShapes.small
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp) ,
                horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start) ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight() ,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Box(
                        modifier = Modifier.background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(workoutMetadata.gradientStart) ,
                                    Color(workoutMetadata.gradientEnd)
                                )
                            )
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                           if(workoutMetadata.dayOfWeek in 0 until 7) stringArrayResource(id = R.array.DaysOfWeek)[workoutMetadata.dayOfWeek].substring(0..2)  else "",
                            fontWeight = FontWeight.Bold ,
                            fontSize = 16.sp ,
                            color = Color.Black
                        )
                    }
                }
                Text(text = workoutMetadata.name , color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }

    private @Composable
    fun EmptySlot(modifier : Modifier , createNewWorkout:()->Unit) {
        Card(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.Top) ,
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer ,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = modifier.then(Modifier.padding(24.dp)) ,
                verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.CenterVertically) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp) ,
                    action = createNewWorkout) { containerColor , contentColor ->

                    Icon(imageVector = Icons.Filled.Add , contentDescription = null)

                }
            }
        }
    }

    @Composable
    fun Headline(modifier : Modifier) {
        Column(
            modifier ,
            horizontalAlignment = Alignment.Start ,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "My\nTraining\nPlan" , style = Typography.headlineLarge)
        }
    }

}
