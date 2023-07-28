package com.example.workoutcompanion.core.presentation.main_navigations.workout_screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.DropDownMenu
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseDocument
import com.example.workoutcompanion.core.data.workout_tracking.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week
import com.example.workoutcompanion.core.data.workout_tracking.workout.WorkoutMetadata
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.core.presentation.main_navigations.screens.training_program_dashboard.AddStartingPointDialogue
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

object WorkoutScreen:MainNavigation.Screens("WorkoutScreen") {

    @Composable
    operator fun invoke(viewModel : WorkoutScreenViewModel , onBackIsPressed : () -> Unit) {

        val metadata by viewModel.metadata.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize() ,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            if (!isLoading) LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp) ,
                verticalArrangement = Arrangement.spacedBy(24.dp , Alignment.Top) ,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Headline(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() ,
                        metadata = metadata ,
                        onBackIsPressed = onBackIsPressed
                    )
                }
                item {
                    ExerciseList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() ,
                        slotList = viewModel.exerciseSlots ,
                        weeksList = viewModel.weeks ,
                        setList = viewModel.sets ,
                        onAddProgression = {
                            viewModel.onAddProgression(it)
                        } ,
                        onSubmitStartingPoint = { slot , reps , weight ->
                            viewModel.onSubmittedStartingPoint(slot , reps , weight)
                        }
                    )
                }

            } else {
                Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(200.dp))
                }
            }
        }

    }

    @Composable
    fun Headline(modifier : Modifier , metadata : WorkoutMetadata , onBackIsPressed : () -> Unit) {
        Column(
            modifier = modifier ,
            verticalArrangement = Arrangement.spacedBy(16.dp , Alignment.Top) ,
            horizontalAlignment = Alignment.Start
        ) {
            IconButton(onClick = onBackIsPressed) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew , contentDescription = null)
            }
            Row(
                modifier = Modifier.wrapContentSize() ,
                horizontalArrangement = Arrangement.spacedBy(12.dp) ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape ,
                    color = Color(metadata.color) ,
                    modifier = Modifier.size(24.dp)
                ) {}
                Text(text = metadata.name , style = Typography.headlineLarge)
            }
        }
    }


    @Composable
    fun ExerciseList(
        modifier : Modifier ,
        slotList : Flow<List<ExerciseSlot>> ,
        weeksList : Flow<List<Week>> ,
        setList : Flow<List<SetSlot>> ,
        onAddProgression : (ExerciseSlot) -> Unit ,
        onSubmitStartingPoint : (ExerciseSlot , Int , Double) -> Unit
    ) {
        val allWeeks by weeksList.collectAsState(initial = emptyList())
        val slots by slotList.collectAsState(emptyList())
        val sets by setList.collectAsState(initial = emptyList())
        Column(
            modifier = modifier ,
            verticalArrangement = Arrangement.spacedBy(4.dp , Alignment.Top) ,
            horizontalAlignment = Alignment.Start
        ) {
            slots.onEach { slot ->

                val slotWeeks = allWeeks.filter { it.exerciseSlotUid == slot.uid }
                val currentSets = sets.filter { it.weekUid in slotWeeks.map { it.uid } }
                ExerciseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp) ,
                    exercise = slot ,
                    weeks = slotWeeks ,
                    sets = currentSets ,
                    onAddWeek = onAddProgression ,
                    onSubmitStartingPoint = onSubmitStartingPoint
                )
            }
        }
    }


    @Composable
    fun ExerciseCard(
        modifier : Modifier ,
        exercise : ExerciseSlot ,
        weeks : List<Week> ,
        sets : List<SetSlot> ,
        onAddWeek : (ExerciseSlot) -> Unit ,
        onSubmitStartingPoint : (ExerciseSlot , Int , Double) -> Unit
    ) {
        Log.d("Test" , sets.size.toString())

        var currentWeek by remember { mutableStateOf(weeks.lastOrNull()) }
        var showDialogue by remember { mutableStateOf(false) }
        Card(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() , colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)){
            Column(
                modifier = modifier ,
                verticalArrangement = Arrangement.spacedBy(16.dp , Alignment.CenterVertically) ,
                horizontalAlignment = Alignment.Start
            ) {
                ExerciseHeadline(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() , slot = exercise,
                    showChart = {

                    },
                    replaceExercise = {

                    },
                    deleteExercise = {

                    },
                    seeExerciseDocument = {

                    }
                )

                AnimatedVisibility(visible = weeks.isEmpty()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable { showDialogue = true } ,
                        verticalAlignment = Alignment.CenterVertically ,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.WarningAmber ,
                            contentDescription = null ,
                            tint = Color.Yellow
                        )
                        Text(text = "Please provide a Starting Point")
                    }
                }

                Column(){
                    if (weeks.isNotEmpty()) {
                        WeekSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight() ,
                            weeks = weeks ,
                            currentWeek = currentWeek ,
                            onWeekClicked = {
                                currentWeek = it
                            } ,
                            onAddProgression = {
                                onAddWeek(exercise)
                            }
                        )
                    }
                    AnimatedContent(targetState = currentWeek) {
                        currentWeek?.let { week ->
                            Column(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth() ,
                                horizontalAlignment = Alignment.Start ,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val currentSets = sets.filter { it.weekUid == week.uid }
                                if (currentSets.isEmpty()) {

                                } else {
                                    currentSets.onEach {
                                        SetCard(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight() ,
                                            setSlot = it
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (showDialogue) {
            AddStartingPointDialogue(onDismiss = { showDialogue = false } ,
                exerciseIsBodyWeight = exercise.isBodyWeight,
                onSubmit = { reps , weight ->
                    onSubmitStartingPoint(exercise , reps , weight)
                    showDialogue = false
                })
        }
    }

    @Composable
    fun SetCard(modifier : Modifier , setSlot : SetSlot ){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.spacedBy(
                24.dp ,
                Alignment.Start
            )
        ) {

            TextBox(
                modifier = Modifier.width(75.dp).height(35.dp) ,
                suffix = "Reps" ,
                text = setSlot.reps.toString() ,
                onValueChanged = {

                }
            )
            Text("X")
            TextBox(
                modifier = Modifier.width(75.dp).height(35.dp) ,
                suffix = "Kgs" ,
                text = String.format("%.1f" , setSlot.weightInKgs) ,
                onValueChanged = {})
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeekSlider(
        modifier : Modifier ,
        weeks : List<Week> ,
        currentWeek : Week? ,
        onWeekClicked : (Week) -> Unit ,
        onAddProgression : () -> Unit
    ) {
        LazyRow(
            modifier = modifier ,
            horizontalArrangement = Arrangement.spacedBy(8.dp) ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(if (weeks.size > 5) weeks.takeLast(5) else weeks , key = { it.uid }) {
                FilterChip(
                    modifier = Modifier.width(75.dp).height(35.dp),
                    selected = if (currentWeek != null) currentWeek.uid == it.uid else false ,
                    onClick = {
                        onWeekClicked(it)
                    } ,
                    label = {
                        Text(
                            "Week ${it.index + 1}" ,
                            fontSize = 10.sp ,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } ,
                    colors = FilterChipDefaults.filterChipColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow , selectedContainerColor = MaterialTheme.colorScheme.primary)
                )
            }

            item {
                AssistChip(onClick = onAddProgression , label = {
                    Icon(imageVector = Icons.Filled.Add , contentDescription = null)
                })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExerciseHeadline(modifier : Modifier , slot : ExerciseSlot , seeExerciseDocument : (ExerciseSlot)->Unit, showChart:(ExerciseSlot)->Unit , replaceExercise:(ExerciseSlot)->Unit , deleteExercise:(ExerciseSlot)->Unit) {

        var showActions by remember { mutableStateOf(false) }
        var tooltipState = rememberRichTooltipState(isPersistent = true)
        val muscleGroups = try {
            slot.muscleGroups.split("/").filter { it.isNotBlank() }.map { it.toInt() }
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList()
        }
        val names = stringArrayResource(id = R.array.MuscleGroups)
        val muscleGroupSt = buildString {
            when {
                muscleGroups.isEmpty() -> append("")
                muscleGroups.size == 1 -> append(names[muscleGroups[0]])
                muscleGroups.size == 2 -> {
                    append(names[muscleGroups[0]])
                    append(" & ")
                    append(names[muscleGroups[1]])
                }
                else -> {
                    muscleGroups.onEachIndexed { index , value ->
                        if (index == 0) {
                            append(names[value])
                        } else if (index != muscleGroups.lastIndex) {
                            append(", ${names[value]} ")
                        } else append(" & ${names[value]}")
                    }
                }
            }
        }
        Row(
            modifier = modifier ,
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.wrapContentSize() ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = slot.exerciseName ,
                    style = Typography.labelLarge ,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = muscleGroupSt ,
                    style = Typography.bodySmall ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.75f)
                )
            }
            RichTooltipBox(
                text = {
                    Column() {
                        val actions = listOf(
                            Triple("See Exercise Document" , Icons.Filled.ArrowCircleRight , seeExerciseDocument),
                            Triple("See Evolution" , Icons.Filled.MultilineChart , showChart) ,
                            Triple(
                                "Replace Exercise" ,
                                Icons.Filled.FindReplace ,
                                replaceExercise
                            ) ,
                            Triple("Remove Exercise" , Icons.Filled.DeleteForever , deleteExercise),
                            Triple("Close" , Icons.Filled.Close) { _ :ExerciseSlot-> showActions = false }
                        )
                        actions.onEach {
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(8.dp)
                                    .clickable {
                                        it.third.invoke(slot)
                                        showActions = false
                                    } ,
                                verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = it.first , fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                Icon(
                                    imageVector = it.second ,
                                    contentDescription = null ,
                                    tint = if (it.third == deleteExercise || it.third == actions.last().third) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary ,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                } ,
                tooltipState = tooltipState ,
                focusable = false ,
                colors = TooltipDefaults.richTooltipColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer ,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionContentColor = MaterialTheme.colorScheme.secondary
                ) ,
                title = {
                    Text(
                        "Actions" ,
                        style = Typography.headlineSmall ,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }) {
                Box(
                    modifier = Modifier.wrapContentHeight() ,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings ,
                        contentDescription = null ,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                            .clickable { showActions = true }
                    )
                }
            }
        }
        LaunchedEffect(key1 = showActions) {
            if (showActions) {
                tooltipState.show()
            } else {
                tooltipState.dismiss()

            }
        }
    }



    @Composable
    fun TextBox(
        modifier : Modifier ,
        suffix : String ,
        text : String ,
        onValueChanged : (String) -> Unit ,
    ) {
        var currentText by remember { mutableStateOf(text) }




        Row(
            modifier = Modifier.wrapContentSize() ,
            horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start) ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier.then(
                    Modifier
                        .background(MaterialTheme.colorScheme.surface , shape = cardShapes.small)
                ) ,
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .padding(12.dp) ,
                    value = currentText ,
                    onValueChange = {
                        currentText = it
                        onValueChanged(currentText)
                    } ,
                    textStyle = TextStyle(
                        fontSize = 12.sp ,
                        color = MaterialTheme.colorScheme.onSurface
                    ))
            }
            Text(
                text = suffix ,
                fontSize = 8.sp ,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        }
    }
}
