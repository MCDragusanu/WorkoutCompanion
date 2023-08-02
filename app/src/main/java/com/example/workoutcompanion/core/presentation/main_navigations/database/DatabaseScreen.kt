package com.example.workoutcompanion.core.presentation.main_navigations.database

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.compose.getPalette
import com.example.workoutcompanion.R
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow

object DatabaseScreen:MainNavigation.Screens("Database Screen") {
    @Composable
    operator fun invoke(
        viewModel : DatabaseScreenViewModel ,
        displayNavBar : () -> Unit ,
        hideNavBar : () -> Unit ,
        onSubmitWorkout : (List<Exercise>) -> Unit ,
    ) {
        val listContent by viewModel.presentedList.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val isInSelectMode by viewModel.isInSelectMode.collectAsState()
        val muscleGroupNames = stringArrayResource(id = R.array.MuscleGroups)
        val selectedExercises by viewModel.selectedUids.collectAsState()

        val lazyListState = rememberLazyListState()

        Scaffold(topBar = {
            AnimatedVisibility(visible = isInSelectMode) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 100.dp)
                        .wrapContentHeight() ,
                    horizontalArrangement = Arrangement.End ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card() {
                        Row(
                            modifier = Modifier.wrapContentSize() ,
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp ,
                                Alignment.CenterHorizontally
                            ) ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { onSubmitWorkout(viewModel.buildWorkout()) }) {
                                Icon(
                                    imageVector = Icons.Filled.CreateNewFolder ,
                                    contentDescription = null
                                )
                            }
                            IconButton(onClick = { viewModel.clearSelectedExercises() }) {
                                Icon(
                                    imageVector = Icons.Filled.ClearAll ,
                                    contentDescription = null
                                )
                            }
                            IconButton(onClick = { viewModel.closeSelectMode() }) {
                                Icon(imageVector = Icons.Filled.Close , contentDescription = null)
                            }
                        }
                    }
                }
            }
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background) ,
                verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Bottom) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(visible = !isLoading) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp) ,
                        state = lazyListState
                    ) {
                        item {
                            Spacer(modifier = Modifier.size(24.dp))

                        }
                        item{
                            Headline()
                        }
                        items(listContent.toList() , key = { it.first.hashCode() }) { pair ->
                            Column(
                                modifier = Modifier.wrapContentHeight() ,
                                horizontalAlignment = Alignment.Start ,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = pair.first ,
                                    style = Typography.headlineMedium ,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                pair.second.onEach {
                                    ExerciseCard(
                                        exercise = it ,
                                        isSelected = it.uid in selectedExercises ,
                                        isInSelectMode = viewModel.isInSelectMode ,
                                        modifier = Modifier.wrapContentHeight() ,
                                        buildMuscleGroupString = { target ->
                                            viewModel.buildMuscleGroupString(
                                                muscleGroupNames ,
                                                exercise = target
                                            )
                                        } ,
                                        onLongClick = {
                                            viewModel.onLongClick(it)
                                        } ,
                                        onClick = {

                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(250.dp) ,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        LaunchedEffect(key1 = lazyListState.firstVisibleItemScrollOffset) {


            if (lazyListState.firstVisibleItemIndex > 0) {
                Log.d("Test" , "hide")
                hideNavBar()
            } else {
                Log.d("Test" , "display")
                displayNavBar()
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
     fun ExerciseCard(
        exercise : Exercise ,
        isInSelectMode : Flow<Boolean> ,
        isSelected : Boolean ,
        modifier : Modifier ,
        onLongClick : (Int) -> Unit ,
        onClick : (Int) -> Unit ,
        buildMuscleGroupString : (Exercise) -> String
    ) {


        val muscleGroupString = buildMuscleGroupString(exercise)
        val muscleGroupId = exercise.movement.primaryMuscleGroups.first().first.ordinal
        val isInSelectedState by isInSelectMode.collectAsState(initial = false)
       Card(
            modifier = modifier.then(Modifier.combinedClickable(onClick = { onClick(exercise.uid) } ,
                onLongClick = { onLongClick(exercise.uid) })) ,
            border = BorderStroke(1.dp , MaterialTheme.colorScheme.surface) ,
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer ,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer ,
                disabledContainerColor = Color.Unspecified ,
                disabledContentColor = Color.Unspecified
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp) ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.wrapContentSize() ,
                    verticalAlignment = Alignment.CenterVertically ,
                    horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start)
                ) {
                   AnimatedVisibility(visible = isSelected) {

                       Box(
                           modifier = Modifier
                               .wrapContentSize()
                               .background(
                                   getPalette().current.successContainer ,
                                   cardShapes.medium
                               )
                       ) {
                           Icon(
                               imageVector = Icons.Filled.Check ,
                               contentDescription = null ,
                               tint = getPalette().current.onSuccessContainer ,
                               modifier = Modifier.padding(4.dp)
                           )
                       }
                   }
                    Column(
                        modifier = Modifier
                            .fillMaxSize() ,
                        horizontalAlignment = Alignment.Start ,
                        verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Top)
                    ) {

                        Text(
                            text = exercise.exerciseName ,
                            fontSize = 14.sp ,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (exercise.movement.type == 0) "Compound" else "Isolation" ,
                            style = Typography.labelSmall ,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                        )
                        Text(
                            text = muscleGroupString , style = Typography.labelSmall ,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                        )
                    }
                }
                /*AnimatedVisibility(visible = isInSelectedState) {
                    RadioButton(selected = isSelected , onClick = { onLongClick(exercise.uid) })
                }*/
            }
        }
    }

    @Composable
    private fun Headline() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp) ,
            horizontalAlignment = Alignment.Start ,
            verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.CenterVertically)
        ) {
            Text(text = "Exercise\nDatabase" , style = Typography.headlineLarge)
        }
    }
}