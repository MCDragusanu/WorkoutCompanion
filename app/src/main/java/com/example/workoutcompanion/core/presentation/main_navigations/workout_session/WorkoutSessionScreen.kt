package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.getPalette
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot

import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

object WorkoutSessionScreen:MainNavigation.Screens("Workout_Session_Screen") {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    operator fun invoke(
        viewModel : WorkoutSessionViewModel ,
        sessionUid : Long ,
        userUid : String
    ) {

        val exerciseSlots by viewModel.exerciseSlots.collectAsState()
        val sets by viewModel.setSlots.collectAsState()


        BottomSheetScaffold(sheetContent = {
                                   WorkoutPlayer(
                                       currentSet = viewModel.currentSet ,
                                       parentExercise =  viewModel.parentExercise,
                                       nextItem = viewModel.nextItem ,
                                       currentSetCompletedInQueue = viewModel.currentSetCompletedInQueue ,
                                       currentSetCompletedForExercise = viewModel.currentSetCompletedForExercise  ,
                                       totalSetsForParent =  viewModel.totalSetsForParent,
                                       totalSets =  viewModel.totalSets,
                                       onNextItem = { viewModel.onNextItem()} ,
                                       onPrevItem = { viewModel.onPrevItem()} ,
                                       updateSetStatus = { state , status->
                                           viewModel.updateStatus(state, status)
                                       }
                                   )
        } ,
            containerColor = MaterialTheme.colorScheme.background) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) ,
                verticalArrangement = Arrangement.spacedBy(12.dp) ,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Text(text = "Today's Workout" , style = Typography.headlineLarge)
                }
                if (exerciseSlots.isNotEmpty()) {
                    items(exerciseSlots ,/* key = { it.slot.uid }*/) { slot ->
                        Column(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .wrapContentHeight() ,
                            horizontalAlignment = Alignment.Start ,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SlotHeadline(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight() , state = slot
                            )
                            SetList(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight() ,
                                sets = sets.filter { it.exerciseSlotUid == slot.uid }
                                    .sortedBy { it.index } ,
                                currentSet = viewModel.currentSet ,
                                onNextItem = { viewModel.onNextItem() } ,
                                onPrevItem = { viewModel.onPrevItem() } ,
                                updateSetStatus = { state , status ->
                                    viewModel.updateStatus(state , status)
                                },
                                onSetIsClicked = {
                                    viewModel.updateCurrentSlot(it)
                                }
                            )
                        }
                    }
                }
            }
        }
        LaunchedEffect(key1 = LocalContext.current) {
            viewModel.retrieveProfile(userUid)
            viewModel.retrieveSession(sessionUid)
        }
    }

    private @Composable
    fun SlotHeadline(modifier : Modifier , state : ExerciseSlot) {
        Column(
            modifier = modifier ,
            horizontalAlignment = Alignment.Start ,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = state.exerciseName , style = Typography.labelLarge)
        }
    }
    private @Composable
    fun WorkoutPlayer(currentSet : Flow<SetSlot?>,
                      parentExercise:Flow<String>,
                      nextItem:Flow<String>,
                      currentSetCompletedInQueue:Flow<Int>,
                      currentSetCompletedForExercise:Flow<Int>,
                      totalSetsForParent:Flow<Int>,
                      totalSets:Flow<Int>,
                      onNextItem : () -> Unit ,
                      onPrevItem : () -> Unit ,
                      updateSetStatus : (state: SetSlot , status: SetSlot.SetStatus) -> Unit) {
        val current by currentSet.collectAsState(initial = null)
        val parentName by parentExercise.collectAsState(initial = "Default")
        val currentProgress by currentSetCompletedForExercise.collectAsState(initial = 0)
        val availableSets by totalSetsForParent.collectAsState(initial = 0)
        val currentPositionInQueue by currentSetCompletedInQueue.collectAsState(initial =0 )
        val queueSize by totalSets.collectAsState(initial = 0)
        val next by nextItem.collectAsState(initial = "")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight().padding(8.dp) ,
            verticalArrangement = Arrangement.spacedBy(12.dp , Alignment.Top) ,
            horizontalAlignment = Alignment.Start
        ) {

            Column() {
                Text(text = "Overall Progress")
                Slider(
                    value = currentPositionInQueue.toFloat() ,
                    onValueChange = {} ,
                    enabled = false ,
                    valueRange = (0.0f..queueSize.toFloat()))
            }
            Column() {
                Column() {
                    Text(
                        text = "Current Exercise" ,
                        style = Typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                        )
                    )
                    Text(
                        text = parentName ,
                        style = Typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
                Slider(
                    value =availableSets.toFloat()- currentProgress.toFloat() ,
                    onValueChange = {} ,
                    enabled = false ,
                    valueRange = (0.0f..availableSets.toFloat()))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onPrevItem() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowCircleLeft ,
                        contentDescription = null
                    )
                }
                IconButton(onClick = { onNextItem() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowCircleRight ,
                        contentDescription = null
                    )
                }
            }
            Column() {
                Text(text = "Next")
                Text(text = next)
            }
        }
    }

    private @Composable
    fun SetList(
        modifier : Modifier ,
        sets : List<SetSlot> ,
        currentSet : Flow<SetSlot?> ,
        onNextItem : () -> Unit ,
        onPrevItem : () -> Unit ,
        onSetIsClicked : (SetSlot) -> Unit,
        updateSetStatus : (state : SetSlot , status : SetSlot.SetStatus) -> Unit ,
    ) {

        Column(
            modifier = modifier ,
            horizontalAlignment = Alignment.Start ,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            sets.onEach {
                SetCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp) ,
                    state = it ,
                    currentSet = currentSet ,
                    onNextItem = onNextItem ,
                    onPrevItem = onPrevItem ,
                    updateSetStatus = updateSetStatus,
                    onSetIsClicked = onSetIsClicked
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private
    @Composable
    fun SetCard(
        modifier : Modifier ,
        state : SetSlot ,
        currentSet : Flow<SetSlot?> ,
        onNextItem : () -> Unit ,
        onPrevItem : () -> Unit ,
        onSetIsClicked:(SetSlot)->Unit,
        updateSetStatus : (state : SetSlot , status : SetSlot.SetStatus) -> Unit
    ) {
        val current by currentSet.collectAsState(initial = null)
        val containerColor = MaterialTheme.colorScheme.primaryContainer
        val scope = rememberCoroutineScope()
        val backgroundColor by animateColorAsState(
            targetValue = if (current != null && current!!.uid == state.uid) MaterialTheme.colorScheme.secondaryContainer else containerColor
        )
        val tooltip = rememberRichTooltipState(isPersistent = true)
        Card(
            modifier = modifier.clickable { onSetIsClicked(state) } ,
            border = BorderStroke(
                2.dp ,
                if (current != null && current!!.uid == state.uid) MaterialTheme.colorScheme.primary else backgroundColor
            ) ,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor ,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) ,
                verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.CenterVertically) ,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxSize() ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize() ,
                        horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start) ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(buildAnnotatedString {
                            append("${state.reps}")
                            withStyle(
                                SpanStyle(
                                    fontSize = 10.sp ,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            ) {
                                append(" Reps")
                            }
                        })
                        Text(text = "X")
                        Text(buildAnnotatedString {
                            append(String.format("%.1f" , state.weightInKgs))
                            withStyle(
                                SpanStyle(
                                    fontSize = 10.sp ,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            ) {
                                append(" Kgs")
                            }
                        })
                    }
                    RichTooltipBox(text = { Text(text = "Complete the Set") } , action = {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight() ,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            FilledTonalButton(onClick = {
                                updateSetStatus(
                                    state ,
                                    SetSlot.SetStatus.Failed
                                )
                                scope.launch {
                                    tooltip.dismiss()
                                }
                            } ,
                                modifier = Modifier.weight(1f , true) ,
                                colors = ButtonDefaults.filledTonalButtonColors()) {
                                Icon(
                                    imageVector = Icons.Filled.Close ,
                                    contentDescription = null ,
                                    tint = MaterialTheme.colorScheme.errorContainer
                                )
                                Text("Failed")
                            }
                            FilledTonalButton(onClick = {
                                updateSetStatus(
                                    state ,
                                    SetSlot.SetStatus.Completed
                                )
                                scope.launch {
                                    tooltip.dismiss()
                                }
                            } ,
                                modifier = Modifier.weight(1f , true) ,
                                colors = ButtonDefaults.filledTonalButtonColors()) {
                                Icon(
                                    imageVector = Icons.Filled.Check ,
                                    contentDescription = null ,
                                    tint = getPalette().current.successColor
                                )
                                Text("Completed")
                            }
                        }
                    } , tooltipState = tooltip) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(30.dp)
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer ,
                                    shape = cardShapes.medium
                                )
                                .clickable {
                                    scope.launch {
                                        tooltip.show()
                                    }
                                } , contentAlignment = Alignment.Center
                        ) {
                            if (state.status != SetSlot.SetStatus.Default.ordinal) {
                                val isFailed = state.status == SetSlot.SetStatus.Failed.ordinal
                                Icon(
                                    imageVector = if (isFailed) Icons.Filled.Close else Icons.Filled.Check ,
                                    contentDescription = null ,
                                    tint = if (isFailed) MaterialTheme.colorScheme.errorContainer else getPalette().current.successColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}