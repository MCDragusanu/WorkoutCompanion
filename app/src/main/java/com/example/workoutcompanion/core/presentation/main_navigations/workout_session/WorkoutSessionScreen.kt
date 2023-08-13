package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.unit.dp
import com.example.compose.getPalette
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot

import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow

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


        BottomSheetScaffold(sheetContent = {} ,
            sheetPeekHeight = 0.dp ,
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
                                onPrevItem = { viewModel.onPrevItem() }
                            )
                        }
                    }
                }
                if(exerciseSlots.isNotEmpty()){
                    item {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() , horizontalArrangement = Arrangement.SpaceBetween , verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.onPrevItem() }) {
                                Icon(imageVector = Icons.Filled.ArrowCircleLeft , contentDescription = null )
                            }
                            IconButton(onClick = { viewModel.onNextItem() }) {
                                Icon(imageVector = Icons.Filled.ArrowCircleRight , contentDescription = null )
                            }
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
    fun SetList(
        modifier : Modifier ,
        sets : List<SetSlot> ,
        currentSet : Flow<SetSlot?> ,
        onNextItem : () -> Unit ,
        onPrevItem : () -> Unit
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
                )
            }
        }
    }

    private @Composable
    fun SetCard(
        modifier : Modifier ,
        state : SetSlot ,
        currentSet : Flow<SetSlot?> ,
        onNextItem : () -> Unit ,
        onPrevItem : () -> Unit ,
    ) {
        val current by currentSet.collectAsState(initial = null)
        val containerColor =
            if (state.type == SetSlot.SetType.WarmUp.ordinal) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
        val backgroundColor by animateColorAsState(
            targetValue = if (current != null && current!!.uid == state.uid) MaterialTheme.colorScheme.tertiaryContainer else containerColor
        )

        Card(
            modifier = modifier ,
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp) ,
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxSize() ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight() ,
                        horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start)
                    ) {
                        Text(text = "${state.reps} reps " , color = Color.White)
                        Text(text = "X" , color = Color.White)
                        Text(
                            text = String.format("%.1f" , state.weightInKgs) + " Kgs" ,
                            color = Color.White
                        )
                       AnimatedVisibility(state.status == SetSlot.SetStatus.Completed.ordinal){
                            Icon(imageVector = Icons.Filled.Check , contentDescription = null , tint = getPalette().current.successColor)
                        }
                        AnimatedVisibility(state.status == SetSlot.SetStatus.Failed.ordinal){
                            Icon(imageVector = Icons.Filled.Error , contentDescription = null , tint = getPalette().current.successColor)
                        }

                    }
                    AnimatedVisibility(visible = current != null && current!!.uid == state.uid) {
                        Row(
                            modifier = Modifier.wrapContentSize() ,
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp ,
                                Alignment.CenterHorizontally
                            ) ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(20.dp)
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer ,
                                        shape = cardShapes.small
                                    ) ,
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack ,
                                    contentDescription = null ,
                                    tint = MaterialTheme.colorScheme.onPrimary ,
                                    modifier = Modifier.clickable { onPrevItem() })
                            }
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(20.dp)
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer ,
                                        shape = cardShapes.small
                                    ) ,
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Check ,
                                    contentDescription = null ,
                                    tint = MaterialTheme.colorScheme.onPrimary ,
                                    modifier = Modifier.clickable { onNextItem() })
                            }
                        }
                    }
                }
            }
        }
    }
}