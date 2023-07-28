package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workoutcompanion.R
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography

object WorkoutScreen:MainNavigation.Screens("Workout Screens") {
    @Composable
    operator fun invoke(
        viewModel : WorkoutViewModel ,
        onNavigateToExerciseDatabase : () -> Unit ,
        onShowNavBar : () -> Unit ,
        onHideNavBar : () -> Unit
    ) {
        val workouts by viewModel.workouts.collectAsState()
        val sets by viewModel.setList.collectAsState()

        val isLoading by viewModel.isLoading.collectAsState()
        var currentWorkoutSelected by remember { mutableStateOf(workouts.indices.first) }
        val scrollState = rememberScrollState()
        var currentChoice by remember { mutableStateOf(0) }
        Scaffold {
            if (!isLoading) Column(
                modifier = Modifier.verticalScroll(scrollState) ,
                verticalArrangement = Arrangement.spacedBy(16.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = "My Workouts" , style = Typography.headlineMedium)
                    if (workouts.isNotEmpty())
                        LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
                            items(workouts.size) {
                                Row(modifier = Modifier.clickable { currentWorkoutSelected = it }) {
                                    Text(text = workouts[it].metadata.name)
                                    if (it == currentWorkoutSelected) {
                                        Icon(
                                            imageVector = Icons.Filled.Check ,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    if (workouts.isNotEmpty()) {
                        Crossfade(targetState = currentWorkoutSelected) {
                            WorkoutCard(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight() ,
                                workout = workouts[currentWorkoutSelected] ,
                                sets = sets,
                                onProvidedStartingPoint = { exerciseSlot , reps , weight , week->
                                     viewModel.onProvidedStartingPoint(exerciseSlot , reps, weight , week)
                                } , onSetModified = { week , slot , field , field2 ->
                                    viewModel.onSetModified(week , slot , field , field2)
                                },
                                onAddWeek = { slot , previousWeek->
                                    viewModel.onAddWeek(slot ,previousWeek)
                                }
                            )
                        }
                    } else {
                        Text(
                            text = "You don't have any workouts created yet!.Press here to create a new one" ,
                            modifier = Modifier.clickable { onNavigateToExerciseDatabase() })
                    }

                }
            }
            else {
                CircularProgressIndicator(modifier = Modifier.size(300.dp))
            }
        }
        LaunchedEffect(key1 = scrollState.value) {
            if (scrollState.value < 50) onShowNavBar()
            else onHideNavBar()
        }
    }

    @Composable
    fun WorkoutCard(
        modifier : Modifier ,
        workout : Workout,
        sets:SetList,
        onAddWeek:(ExerciseSlot , Week)->Unit,
        onSetModified : (Week , SetSlot , Field.Reps , Field.Weight) -> Int? ,
        onProvidedStartingPoint : (ExerciseSlot , Field.Reps , Field.Weight, Week) -> Unit
    ) {
        Column(modifier = modifier , verticalArrangement = Arrangement.spacedBy(24.dp)) {
            WorkoutHeadline(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() , workoutMetadata = workout.metadata
            )
            workout.getEntries().onEach { entry ->
                var currentWeek by mutableStateOf(entry.second.last())
                var currentSets by remember { mutableStateOf(sets.getSets(currentWeek.uid))}
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    horizontalAlignment = Alignment.Start ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = entry.first.exerciseName , style = Typography.headlineSmall)
                    LazyRow(verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(entry.second) { week ->
                            Surface(modifier = Modifier.clickable {
                                currentWeek = entry.second[week.index]
                                currentSets = sets.getSets(currentWeek.uid)
                            }) {
                                Text(text = "Week ${week.index + 1}")
                            }
                        }
                        item {
                            IconButton(onClick = {if(currentSets.isNotEmpty()) onAddWeek(entry.first , entry.second.last()) }) {
                                Icon(imageVector = Icons.Filled.Add , contentDescription = null)
                            }
                        }
                    }
                    SetList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() ,
                        week = currentWeek ,
                        sets = currentSets,
                        onSetModified = onSetModified,
                        onProvidedStartingPoint = {  reps , weight , week->
                            onProvidedStartingPoint(entry.first , reps , weight , week )
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun SetList(
        modifier : Modifier ,
        week : Week ,
        sets :  List<SetSlot>,
        onProvidedStartingPoint:(Field.Reps ,Field.Weight, Week)->Unit,
        onSetModified : ( Week,SetSlot , Field.Reps , Field.Weight) -> Int?
    ) {

        var showDialogue by remember { mutableStateOf(false) }
        if (sets.isEmpty()) {
            Text(
                text = "Please Provide a starting point" ,
                modifier = Modifier.clickable { showDialogue = true })
        } else {
            Column(
                modifier = modifier ,
                verticalArrangement = Arrangement.spacedBy(8.dp) ,
                horizontalAlignment = Alignment.Start
            ) {
                sets.onEach { set ->
                    var repsErrorCode by remember { mutableStateOf<Int?>(null) }
                    var weightErrorCode by remember { mutableStateOf<Int?>(null) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() ,
                        verticalAlignment = Alignment.CenterVertically ,
                        horizontalArrangement = Arrangement.spacedBy(12.dp , Alignment.Start)
                    ) {
                        var repsText by remember { mutableStateOf(set.reps.toString()) }
                        var weightText by remember {
                            mutableStateOf(
                                String.format(
                                    "%.01f" ,
                                    set.weightInKgs
                                )
                            )
                        }
                        val repFieldInteractionSource = MutableInteractionSource()
                        val weightFieldInteractionSource = MutableInteractionSource()
                        val repsIsPressed by repFieldInteractionSource.collectIsFocusedAsState()
                        val weightIsPressed by weightFieldInteractionSource.collectIsFocusedAsState()
                        var textFieldIsSelected by mutableStateOf(false)
                        Text((set.index + 1).toString() + ".")

                        TextField(value = repsText , onValueChange = {
                            repsText = it
                        } , suffix = {
                            Text(text = "Reps")
                        }, interactionSource = repFieldInteractionSource , modifier = Modifier
                            .width(125.dp))

                        Text(text = "X")


                        TextField(value = weightText , onValueChange = {
                            weightText = it
                        } , suffix = {
                            Text(text = "Kgs")
                        } , interactionSource = weightFieldInteractionSource
                            , modifier = Modifier
                            .width(125.dp))
                        AnimatedVisibility(visible = repsIsPressed||weightIsPressed) {
                            IconButton(onClick = {
                                val result = onSetModified(
                                    week ,
                                    set ,
                                    Field.Reps(repsText) ,
                                    Field.Weight(weightText)
                                )
                                if(result == null){
                                    textFieldIsSelected = false
                                }
                            }) {
                                Icon(imageVector = Icons.Filled.Check , contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
        if (showDialogue) {
            var repsString by remember { mutableStateOf("") }
            var weightString by remember { mutableStateOf("") }
            var state by remember {
                mutableStateOf<Pair<Pair<Field.Reps , Int?> , Pair<Field.Weight , Int?>>?>(
                    null
                )
            }
            Dialog(onDismissRequest = { showDialogue = false }) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface) , contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(16.dp) , verticalArrangement = Arrangement.spacedBy(24.dp)) {
                        Text(text = stringResource(R.string.enter_starting_point_message))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight() ,
                            horizontalArrangement = Arrangement.SpaceBetween ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = repsString ,
                                onValueChange = { repsString = it } ,
                                suffix = {
                                    Text(text = "Reps")
                                }, modifier = Modifier.weight(1f ,true))
                            OutlinedTextField(
                                value = weightString ,
                                onValueChange = { weightString = it } ,
                                suffix = {
                                    Text(text = "Kgs")
                                } , modifier = Modifier.weight(1f ,true))
                        }
                        FilledTonalButton(onClick = {
                            onProvidedStartingPoint(
                                Field.Reps(repsString) ,
                                Field.Weight(weightString) ,
                                week
                            )
                            showDialogue = false
                        }) {
                             Text(text = "Save Result")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun WorkoutHeadline(modifier : Modifier , workoutMetadata : WorkoutMetadata) {
        Column(
            modifier = modifier ,
            verticalArrangement = Arrangement.spacedBy(12.dp) ,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = workoutMetadata.name)
        }
    }
}
