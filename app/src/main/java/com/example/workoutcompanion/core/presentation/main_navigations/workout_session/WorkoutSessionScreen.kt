package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.getPalette
import com.example.workoutcompanion.common.Resource
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton
import com.example.workoutcompanion.common.composables.AnimatedStatefulCallToAction
import com.example.workoutcompanion.common.composables.UIState
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot

import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

object WorkoutSessionScreen:MainNavigation.Screens("Workout_Session_Screen") {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    operator fun invoke(
        viewModel : WorkoutSessionViewModel ,
        onBackIsPressed : () -> Unit,
        sessionUid : Long ,
        userUid : String
    ) {

        val exerciseSlots by viewModel.exerciseSlots.collectAsState()
        val sets by viewModel.setSlots.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        val lazyListState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        var currentIndex by remember { mutableStateOf(0)}
        val bottomSheetState = rememberBottomSheetScaffoldState()
        val reports by viewModel.exerciseReports.onEach { if(it.isNotEmpty())bottomSheetState.bottomSheetState.expand() }.collectAsState(
            emptyList())
        BottomSheetScaffold( scaffoldState = bottomSheetState, sheetContent = {
                                                                              SessionReportSnackbar(
                                                                                  onDismiss = { scope.launch{ bottomSheetState.bottomSheetState.hide() } } ,
                                                                                  results =reports ,
                                                                                  onRegress = {

                                                                                  } ,
                                                                                  onStatusQuo ={

                                                                                  }  ,
                                                                                  onProgress = {

                                                                                  }
                                                                              )
        } , sheetContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            sheetPeekHeight = 0.dp ,
            sheetSwipeEnabled = false,
            containerColor = MaterialTheme.colorScheme.background) {
            Column() {
                if (!isLoading) LazyColumn(
                    state = lazyListState ,
                    modifier = Modifier
                        .weight(5f)
                        .padding(8.dp) ,
                    verticalArrangement = Arrangement.spacedBy(4.dp) ,
                    horizontalAlignment = Alignment.Start
                ) {
                    item {
                        WorkoutSessionHeadline(
                            modifier = Modifier
                                .fillMaxWidth() ,
                            onBackIsPressed
                        )
                    }
                    exerciseSlots.onEach { slot ->
                        item { Spacer(modifier = Modifier.size(8.dp)) }
                        item {
                            SlotHeadline(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .wrapContentHeight() , state = slot
                            )
                        }
                        item { Spacer(modifier = Modifier.size(8.dp)) }
                        items(sets.filter { it.exerciseSlotUid == slot.uid }
                            .sortedBy { it.index } , key = { it.uid }) {
                            SetCard(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .height(50.dp) ,
                                state = it ,
                                currentSet = viewModel.currentSet ,
                                updateSetStatus = { state , status ->
                                    viewModel.updateStatus(state , status)
                                } ,
                                onSetIsClicked = {
                                    viewModel.updateCurrentSlot(it)
                                }
                            )
                        }
                    }
                }
                if (!isLoading) WorkoutPlayer(
                    currentSet = viewModel.currentSet ,
                    parentExercise = viewModel.parentExercise ,
                    nextItem = viewModel.nextItem ,
                    workoutIsCompleted = viewModel.workoutIsCompleted ,
                    btnSaveWorkoutState = viewModel.btnSaveWorkoutState ,
                    currentSetCompletedForExercise = viewModel.currentSetCompletedForExercise ,
                    totalSetsForParent = viewModel.totalSetsForParent ,
                    onNextItem = {
                        val where = viewModel.onNextItem()
                        if (where!=null) scope.launch {
                            lazyListState.animateScrollToItem(
                                where
                            )
                        }
                    } ,
                    onPrevItem = {
                        val where = viewModel.onPrevItem()
                        if (where!=null) scope.launch {
                            lazyListState.animateScrollToItem(
                                where
                            )
                        }
                    } ,
                    updateSetStatus = { state , status ->
                        viewModel.updateStatus(state , status)
                    } ,
                    onCompleteWorkout = {
                        viewModel.onCompleteSession()
                    }
                )
            }
        }

        var showReportDialogue by remember { mutableStateOf(false)}
        LaunchedEffect(key1 = LocalContext.current) {
            viewModel.retrieveProfile(userUid)
            viewModel.retrieveSession(sessionUid)
        }
        LaunchedEffect(key1 = reports){
            showReportDialogue = true
           if(reports.isNotEmpty()) bottomSheetState.bottomSheetState.expand()
        }

    }

    private @Composable
    fun WorkoutSessionHeadline(modifier : Modifier ,onBackIsPressed:()->Unit) {
        Column(modifier = modifier , horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = onBackIsPressed) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew , contentDescription = null)
            }
            Text(text = "Current\nWorkout Session" , style = Typography.headlineMedium)
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
                      workoutIsCompleted :Flow<Boolean>,
                      currentSetCompletedForExercise:Flow<Int>,
                      totalSetsForParent:Flow<Int>,
                      btnSaveWorkoutState:Flow<UIState>,
                      onCompleteWorkout:()->Unit,
                      onNextItem : () -> Unit ,
                      onPrevItem : () -> Unit ,
                      updateSetStatus : (state: SetSlot , status: SetSlot.SetStatus) -> Unit) {
        val current by currentSet.collectAsState(initial = null)
        val parentName by parentExercise.collectAsState(initial = "Default")
        val currentProgress by currentSetCompletedForExercise.onEach {
            Log.d(
                "Test" ,
                it.toString()
            )
        }.collectAsState(initial = 0)
        val availableSets by totalSetsForParent.onEach { Log.d("Test" , it.toString()) }
            .collectAsState(initial = 0)

        val next by nextItem.collectAsState(initial = "")
        val workoutCompleted = workoutIsCompleted.collectAsState(initial = false)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp) ,
            verticalArrangement = Arrangement.spacedBy(24.dp , Alignment.Top) ,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp) ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp) ,
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onPrevItem() }) {
                    Icon(imageVector = Icons.Filled.FastRewind , contentDescription = null)
                }
                Surface(shape = cardShapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer ,
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .clickable {
                            current?.let {
                                updateSetStatus(it , SetSlot.SetStatus.Failed)
                                onNextItem()
                            }
                        }) {
                    Icon(imageVector = Icons.Filled.Close , contentDescription = null , modifier = Modifier.size(16.dp))
                }
                Surface(
                    shape = cardShapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer ,
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .clickable {
                            current?.let {
                                updateSetStatus(it , SetSlot.SetStatus.Completed)
                                onNextItem()
                            }
                        }) {


                    Icon(imageVector = Icons.Filled.Check , contentDescription = null,modifier = Modifier.size(16.dp))

                }
                IconButton(onClick = { onNextItem() }) {
                    Icon(imageVector = Icons.Filled.FastForward , contentDescription = null)
                }
            }


            AnimatedVisibility(visible = !workoutCompleted.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Next")
                    Text(text = next ,)
                }
            }
            AnimatedVisibility(visible = workoutCompleted.value) {
                AnimatedStatefulCallToAction(
                    ctaState = btnSaveWorkoutState ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) ,
                    action = onCompleteWorkout
                ) { state , container , content ->
                    when (state) {
                        UIState.Loading -> {
                            LinearProgressIndicator()
                        }
                        UIState.Enabled -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp) ,
                                verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.Center
                            ) { Text(text = "Save Session") }
                        }
                        UIState.Error -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp) ,
                                verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.Center
                            ) { Text(text = "Not All Sets are tracked") }
                        }
                        UIState.Completed -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp) ,
                                verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Session Saved")
                            }
                        }
                        else -> {

                        }
                    }
                }
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