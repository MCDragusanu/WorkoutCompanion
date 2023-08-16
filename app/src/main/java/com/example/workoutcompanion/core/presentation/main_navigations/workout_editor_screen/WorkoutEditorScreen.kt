package com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.getPalette
import com.example.workoutcompanion.R
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.week.Week
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.domain.model.exercise.Exercise
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.core.presentation.main_navigations.screens.training_program_dashboard.RepsAndWeightDialogue
import com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen.dialogue.ChangeRestTimeDialogue
import com.example.workoutcompanion.on_board.registration.screens.feature.ItemSlider
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

object WorkoutEditorScreen:MainNavigation.Screens("WorkoutScreen") {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    operator fun invoke(
        viewModel : WorkoutEditorViewModel ,
        onNavigateToSessionScreen : (Long) -> Unit ,
        onBackIsPressed : () -> Unit ,
        onMetadataChanged : (WorkoutMetadata) -> Unit
    ) {

        val metadata by viewModel.metadata.onEach { onMetadataChanged(it) }.collectAsState(
            WorkoutMetadata(0 , guestProfile.uid , "Default Workout" , "" , 1 , dayOfWeek = 0)
        )
        val state by viewModel.appState.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        var showColorDialogue by remember { mutableStateOf(false) }
        var showNameDialogue by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }
        val currentError by viewModel.errorChannel.collectAsState(initial = "")
        val scaffoldState =
            rememberBottomSheetScaffoldState(bottomSheetState = SheetState(skipPartiallyExpanded = true))
        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize() ,
            scaffoldState = scaffoldState ,
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) } ,
            containerColor = MaterialTheme.colorScheme.background ,
            sheetContent = {
                AddExerciseBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f) ,
                    bottomSheetIsLoading = viewModel.bottomSheetIsLoading ,
                    exerciseCollection = viewModel.exerciseCollection ,
                    onSearchExercise = { text ->
                        viewModel.onSearchExercise(text)
                    } ,
                    onAddExercise = {
                        viewModel.addExercise(it)
                        scope.launch {
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                )
            } ,
            sheetContainerColor = getPalette().current.tertiarySurfaceColor ,
            sheetContentColor = MaterialTheme.colorScheme.onBackground ,
            sheetSwipeEnabled = false ,
            sheetPeekHeight = 0.dp ,
            sheetShape = cardShapes.extraLarge ,
        ) {
            if (!isLoading) LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp) ,
                verticalArrangement = Arrangement.spacedBy(48.dp , Alignment.Top) ,
                horizontalAlignment = Alignment.Start
            ) {
                item(0) {
                    Spacer(modifier = Modifier.size(1.dp))
                }
                item(1) {
                    WorkoutSummary(modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp) ,
                        expectedLengthInMinutes = 90 ,
                        metadata = metadata ,
                        currentWeek = 1 ,
                        onBackIsPressed = onBackIsPressed ,
                        sessionStatus = viewModel.btnStartWorkout,
                        onChangeLabelColor = {
                            showColorDialogue = true
                        } ,
                        onEditWorkoutName = {
                            showNameDialogue = true
                        } , onChangeDayOfWeek = {

                        } , onStartWorkout = {
                            viewModel.onStartWorkout(onNavigateToSessionScreen)
                        })
                }
                item(2) {
                    ExerciseList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() ,
                        slotList = viewModel.exerciseSlots ,
                        weeksList = viewModel.weeks ,
                        setList = viewModel.sets ,
                        onAddNewSet = { type , reps , weight , week ->
                            viewModel.addNewSet(type , reps , weight , week)
                        } ,
                        onSubmitStartingPoint = { slot , reps , weight ->
                            viewModel.onSubmittedStartingPoint(slot , reps , weight)
                        } ,
                        onSetChanged = {
                            Log.d("Test" , "Set that will be changed = $it")
                            viewModel.onSetChanged(it)
                        } ,
                        onDeleteExerciseSlot = {
                            viewModel.deleteExerciseSlot(it)
                        } ,
                        onAddExercise = {
                            scope.launch { scaffoldState.bottomSheetState.expand() }
                        } ,
                        onRemoveSet = {
                            viewModel.removeSet(it)
                        } ,
                        onGenerateWarmUpSets = {
                            //  viewModel.generateWarmUpSets(it)
                        } ,
                        getRestTimeForWorkingSets = { slot ->
                            state?.let {

                                when (slot.category) {
                                    Exercise.Companion.ExerciseCategory.PrimaryCompound.ordinal -> it.trainingParameters.primaryCompoundSchema.workingSetRestTimeInSeconds
                                    Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> it.trainingParameters.secondaryCompoundSchema.workingSetRestTimeInSeconds
                                    else -> it.trainingParameters.isolationSchema.workingSetRestTimeInSeconds
                                }
                            } ?: 60

                        } ,
                        getRestTimeForWarmUpSets = { slot ->
                            state?.let {

                                when (slot.category) {
                                    Exercise.Companion.ExerciseCategory.PrimaryCompound.ordinal -> it.trainingParameters.primaryCompoundSchema.warmUpSetRestTimeInSeconds
                                    Exercise.Companion.ExerciseCategory.SecondaryCompound.ordinal -> it.trainingParameters.secondaryCompoundSchema.warmUpSetRestTimeInSeconds
                                    else -> it.trainingParameters.isolationSchema.warmUpSetRestTimeInSeconds
                                }
                            } ?: 60
                        } ,
                        onEditRestTime = { slot , type , value ->
                            Log.d("Test" , "Type = ${type}")
                            viewModel.onEditRestTime(slot , type , value)
                        }

                    )
                }

            } else {
                Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(200.dp))
                }
            }
        }

        if (showColorDialogue) {
            ColorPicker(
                title = "Pick the color" ,
                description = "Choose what color will be mapped to this workout" ,
                current = Pair(Color(metadata.gradientStart) , Color(metadata.gradientEnd)) ,
                onDismiss = { showColorDialogue = false } ,
                onSubmitColor = {
                    viewModel.updateColors(it)
                }
            )
        }
        if (showNameDialogue) {
            ChangeWorkoutNameDialogue(onDismiss = { showNameDialogue = false } ,
                text = metadata.name ,
                onSubmit = {
                    viewModel.updateMetadata(metadata.copy(name = it))
                    showNameDialogue = false
                    onMetadataChanged(metadata)
                })
        }
        LaunchedEffect(key1 = currentError) {
            Log.d("Test" , "Error changed")
            if (currentError.isNotBlank()) {
                val result = snackBarHostState.showSnackbar(currentError)

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private
    @Composable
    fun AddExerciseBottomSheet(
        modifier : Modifier ,
        bottomSheetIsLoading : StateFlow<Boolean> ,
        exerciseCollection : StateFlow<List<Exercise>> ,
        onSearchExercise : (String) -> Unit ,
        onAddExercise : (Exercise) -> Unit
    ) {
        val isLoading by bottomSheetIsLoading.collectAsState()
        val exerciseList by exerciseCollection.collectAsState()
        var currentText by remember { mutableStateOf("") }

        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) ,
            verticalArrangement = Arrangement.spacedBy(4.dp , alignment = Alignment.Top) ,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Add new Exercise" , style = Typography.headlineSmall)
            Spacer(modifier = Modifier.size(12.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                value = currentText ,
                placeholder = {
                    Text(text = "Search any exercise")
                } ,
                onValueChange = {
                    currentText = it
                    onSearchExercise(it)
                } ,
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search , contentDescription = null)
                } ,
                trailingIcon = {
                    AnimatedVisibility(visible = isLoading) {
                        CircularProgressIndicator()
                    }
                } ,
            )
            exerciseList.onEach {
                ExerciseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() , it ,
                    onSubmitExercise = onAddExercise
                )
            }
        }
    }

    @Composable
    fun ExerciseCard(
        modifier : Modifier ,
        exercise : Exercise ,
        onSubmitExercise : (Exercise) -> Unit
    ) {
        val names = stringArrayResource(id = R.array.MuscleGroups)
        Card(
            modifier = modifier.clickable { onSubmitExercise(exercise) } ,
            colors = CardDefaults.cardColors(containerColor = getPalette().current.secondarySurfaceColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp) ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = exercise.exerciseName , style = Typography.labelMedium ,)

                val muscleGroupsSt = buildString {
                    val list =
                        (exercise.movement.primaryMuscleGroups + exercise.movement.secondaryMuscleGroups).map { it.first.ordinal }
                    list.onEachIndexed { index , i ->
                        when (list.size) {
                            1 -> {
                                append(names[i])
                            }
                            2 -> {
                                append(names[0])
                                append(" & ")
                                append(names[1])
                            }
                            else -> {
                                if (index != list.indices.last) {
                                    append(names[i])
                                    append(" ")
                                } else append(" & ${names[i]}")
                            }
                        }
                        if (list.size == 1) {
                            append(names[i])
                        }
                    }
                }
                Text(
                    muscleGroupsSt ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f) ,
                    style = Typography.labelSmall
                )
                Text(
                    text = if (exercise.movement.type == 0) "Compound" else "Isolation" ,
                    style = Typography.labelSmall ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                )
            }
        }
    }


    private @Composable
    fun WorkoutSummary(
        modifier : Modifier ,
        metadata : WorkoutMetadata ,
        expectedLengthInMinutes : Int ,
        currentWeek : Int ,
        sessionStatus:Flow<Boolean>,
        onBackIsPressed : () -> Unit ,
        onEditWorkoutName : () -> Unit ,
        onChangeDayOfWeek : () -> Unit ,
        onChangeLabelColor : () -> Unit ,
        onStartWorkout : () -> Unit
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth() ,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew ,
                contentDescription = null ,
                modifier = Modifier.clickable { onBackIsPressed() })

            Text(text = "Summary" , style = Typography.headlineMedium)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2) ,
                modifier = modifier ,
                userScrollEnabled = false ,
                horizontalArrangement = Arrangement.spacedBy(4.dp) ,
                verticalArrangement = Arrangement.spacedBy(4.dp) ,
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(metadata.gradientStart) ,
                                        Color(metadata.gradientEnd)
                                    ) ,
                                ) , shape = cardShapes.medium
                            )
                            .clickable { onChangeLabelColor() }
                            .height(100.dp) , contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp) ,
                            verticalArrangement = Arrangement.Center ,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Label\nColor" ,
                                style = Typography.headlineSmall ,
                                color = Color.Black.copy(0.75f)
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .clickable {
                                onEditWorkoutName()
                            }
                            .height(100.dp) ,
                        shape = cardShapes.medium ,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize() ,
                            verticalArrangement = Arrangement.spacedBy(12.dp) ,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Name" ,
                                style = Typography.labelSmall ,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            )
                            Text(text = metadata.name , style = Typography.headlineSmall)
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.height(100.dp) ,
                        shape = cardShapes.medium ,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize() ,
                            verticalArrangement = Arrangement.spacedBy(12.dp) ,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Est. Workout Duration" ,
                                style = Typography.labelSmall ,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            )
                            val hours = expectedLengthInMinutes / 60
                            val remaining = expectedLengthInMinutes - hours * 60
                            val st =
                                if (hours == 0) "$remaining Min" else "${hours}h : ${remaining} Min"
                            Text(text = st , style = Typography.headlineMedium)
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .clickable {
                                onChangeDayOfWeek()
                            }
                            .height(100.dp) ,
                        shape = cardShapes.medium ,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize() ,
                            verticalArrangement = Arrangement.spacedBy(12.dp) ,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Day of Week" ,
                                style = Typography.labelSmall ,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            )
                            Text(
                                text = if (metadata.dayOfWeek >= 7) "Not set" else stringArrayResource(
                                    id = R.array.DaysOfWeek
                                )[metadata.dayOfWeek % 7] ,
                                style = Typography.headlineSmall
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.height(100.dp) ,
                        shape = cardShapes.medium ,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize() ,
                            verticalArrangement = Arrangement.spacedBy(12.dp) ,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Current Progress" ,
                                style = Typography.labelSmall ,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            )
                            Text(
                                text = "${currentWeek + 1} / ${metadata.programLengthInWeeks} weeks" ,
                                style = Typography.headlineSmall
                            )
                        }
                    }
                }
                item {
                    val isActive by sessionStatus.collectAsState(initial = false)
                    Card(
                        modifier = Modifier
                            .clickable {
                                Log.d("Bug" , "On Click Called Once")
                                onStartWorkout()
                            }
                            .height(100.dp) ,
                        shape = cardShapes.medium ,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize() ,
                            verticalArrangement = Arrangement.SpaceEvenly ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = if(!isActive)"Start Workout" else "Resume Workout" , style = Typography.labelMedium)
                            Icon(
                                imageVector = Icons.Filled.PlayCircleFilled ,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun ExerciseList(
        modifier : Modifier ,
        slotList : StateFlow<List<ExerciseSlot>> ,
        weeksList : StateFlow<List<Week>> ,
        setList : StateFlow<List<SetSlot>> ,
        onAddNewSet : (Int , Int , Double , Week) -> Unit ,
        onSubmitStartingPoint : (ExerciseSlot , Int , Double) -> Unit ,
        onDeleteExerciseSlot : (ExerciseSlot) -> Unit ,
        onSetChanged : (SetSlot) -> Unit ,
        onAddExercise : () -> Unit ,
        onRemoveSet : (SetSlot) -> Unit ,
        onGenerateWarmUpSets : (Week) -> Unit ,
        getRestTimeForWarmUpSets : (ExerciseSlot) -> Int ,
        getRestTimeForWorkingSets : (ExerciseSlot) -> Int ,
        onEditRestTime : (ExerciseSlot , Int , Int) -> Unit ,
    ) {
        val allWeeks by weeksList.collectAsState()
        val slots by slotList.collectAsState()
        val sets by setList.collectAsState()
        val listState = rememberLazyListState()
        val currentItem = MutableStateFlow(0)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp) , horizontalAlignment = Alignment.CenterHorizontally) {
            ExerciseListHeadline(
                modifier = Modifier.fillMaxWidth() ,
                onChangeOrder = { } ,
                onAddExercise = onAddExercise)
            Spacer(modifier = Modifier.size(24.dp))
            ItemSlider(
                modifier = Modifier.wrapContentHeight() ,
                currentItem = currentItem,
                numberOfItems =  slots.size ,
                verticalAlignment =  Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )
            LazyRow(
                modifier = modifier ,
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (slots.isNotEmpty())
                    items(slots , key = { it.uid }) { slot ->
                        val slotWeeks = allWeeks.filter { it.exerciseSlotUid == slot.uid }
                        val currentSets = sets.filter { it.weekUid in slotWeeks.map { it.uid } }
                            .sortedBy { it.weekUid }
                        ExerciseCard(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp) ,
                            exercise = slot ,
                            weeks = slotWeeks ,
                            sets = currentSets ,
                            onAddSet = onAddNewSet ,
                            onSubmitStartingPoint = onSubmitStartingPoint ,
                            onSetChanged = onSetChanged ,
                            onDeleteExerciseSlot = onDeleteExerciseSlot ,
                            onRemoveSet = onRemoveSet ,
                            onGenerateWarmUpSets = onGenerateWarmUpSets ,
                            getRestTimeForWarmUpSets = getRestTimeForWarmUpSets ,
                            getRestTimeForWorkingUpSets = getRestTimeForWorkingSets ,
                            onEditRestTime = onEditRestTime
                        )
                    }
                else item {
                    Card(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 16.dp) ,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp) ,
                            horizontalAlignment = Alignment.CenterHorizontally ,
                            verticalArrangement = Arrangement.spacedBy(
                                16.dp ,
                                Alignment.CenterVertically
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FormatListBulleted ,
                                contentDescription = null ,
                                modifier = Modifier.size(150.dp)
                            )
                            Text(
                                text = "It seems you don't have any exercises added to this workout!" ,
                                style = Typography.bodyLarge ,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Start adding now" ,
                                color = MaterialTheme.colorScheme.secondary ,
                                style = Typography.labelMedium ,
                                modifier = Modifier.clickable { onAddExercise() }
                            )
                        }
                    }
                }
            }
        }
        LaunchedEffect(key1 = listState.firstVisibleItemIndex){
            currentItem.emit(listState.firstVisibleItemIndex)
        }

    }



    @Composable
    fun ExerciseListHeadline(
        modifier : Modifier ,
        onChangeOrder : () -> Unit ,
        onAddExercise : () -> Unit
    ) {
        Column(
            modifier = modifier ,
            verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Top) ,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Exercise\nComposition" , style = Typography.headlineMedium)
                Row(
                    modifier = Modifier.wrapContentSize() ,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) ,
                    Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(32.dp) ,
                        color = MaterialTheme.colorScheme.surface ,
                        contentColor = MaterialTheme.colorScheme.primary ,
                        shape = cardShapes.small
                    ) {
                        IconButton(onClick = { onChangeOrder() }) {
                            Icon(imageVector = Icons.Filled.Reorder , contentDescription = null)
                        }
                    }
                    Surface(
                        modifier = Modifier.size(32.dp) ,
                        shape = cardShapes.small ,
                        color = MaterialTheme.colorScheme.surface ,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        IconButton(onClick = { onAddExercise() }) {
                            Icon(imageVector = Icons.Filled.Add , contentDescription = null)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ExerciseCard(
        modifier : Modifier ,
        exercise : ExerciseSlot ,
        weeks : List<Week> ,
        sets : List<SetSlot> ,
        onAddSet : ( Int,Int , Double , Week) -> Unit ,
        onSubmitStartingPoint : (ExerciseSlot , Int , Double) -> Unit ,
        onSetChanged : (SetSlot) -> Unit ,
        onDeleteExerciseSlot : (ExerciseSlot) -> Unit ,
        onRemoveSet : (SetSlot) -> Unit,
        getRestTimeForWarmUpSets:(ExerciseSlot)->Int,
        getRestTimeForWorkingUpSets:(ExerciseSlot)->Int,
        onGenerateWarmUpSets : (Week) -> Unit,
        onEditRestTime:(ExerciseSlot,Int,Int)->Unit
    ) {


        var showRestTimeDialogue by remember { mutableStateOf(false)}
        var restTimeType by remember { mutableStateOf(0)}
        var currentWeek by remember { mutableStateOf(weeks.lastOrNull()) }
        val lastWeek = weeks.lastOrNull()
        var showDialogue by remember { mutableStateOf(false) }
        var showDialogueAddSets by remember { mutableStateOf(false) }
        val currentSets =
            if (currentWeek == null) emptyList() else sets.filter { it.weekUid == currentWeek!!.uid }
                .sortedBy { it.index }
        var setType by remember { mutableStateOf(SetSlot.SetType.WorkingSet.ordinal) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() ,
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = modifier ,
                verticalArrangement = Arrangement.spacedBy(16.dp , Alignment.CenterVertically) ,
                horizontalAlignment = Alignment.Start
            ) {
                ExerciseHeadline(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() , slot = exercise ,
                    showChart = {

                    } ,
                    replaceExercise = {

                    } ,
                    deleteExercise = {
                        onDeleteExerciseSlot(it)
                    } ,
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

                Column() {
                    if (weeks.isNotEmpty()) {
                        WeekSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp) ,
                            weeks = weeks ,
                            currentWeek = currentWeek ,
                            onWeekClicked = {
                                currentWeek = it
                            } ,

                            )
                    }
                    AnimatedContent(targetState = currentSets) {
                        currentWeek?.let { week ->
                            Column(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth() ,
                                horizontalAlignment = Alignment.Start ,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                val warmUpSets = it.filter { it.type == SetSlot.SetType.WarmUp.ordinal }
                                val workingSets = it.filter { it.type == SetSlot.SetType.WorkingSet.ordinal }
                                Column(
                                    modifier = Modifier.wrapContentSize() ,
                                    verticalArrangement = Arrangement.spacedBy(4.dp) ,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "Warm Up Sets" , style = Typography.labelMedium)

                                        Icon(
                                            imageVector = Icons.Filled.Add ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.secondary ,
                                            modifier = Modifier.clickable {
                                                showDialogueAddSets = true
                                                setType = SetSlot.SetType.WarmUp.ordinal
                                            }
                                        )

                                    }
                                    Row(
                                        modifier = Modifier.wrapContentSize() ,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            8.dp ,
                                            Alignment.Start
                                        ) ,
                                        verticalAlignment = Alignment.CenterVertically,

                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Timer ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.primary
                                        )

                                        Text(
                                            text = "${getRestTimeForWarmUpSets(exercise)} s" ,
                                            color = MaterialTheme.colorScheme.primary ,
                                            modifier = Modifier.clickable {
                                                restTimeType = 0
                                                showRestTimeDialogue = true

                                            })
                                        Text(
                                                text = "Rest Time between sets" ,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                }
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (warmUpSets.isEmpty()) {
                                        Text(
                                            text = "No Warm Up Sets" ,
                                            style = Typography.labelSmall
                                        )
                                    } else {
                                        warmUpSets.onEach {
                                            SetCard(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight() ,
                                                setSlot = it ,
                                                isEditable = currentWeek == lastWeek ,
                                                onSetChanged = onSetChanged ,
                                                isBodyWeight = exercise.isBodyWeight ,
                                                onRemoveSet = { toBeRemoved ->
                                                    onRemoveSet(toBeRemoved)
                                                }
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.wrapContentSize() ,
                                    verticalArrangement = Arrangement.spacedBy(4.dp) ,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "Working Sets" , style = Typography.labelMedium)

                                        Icon(
                                            imageVector = Icons.Filled.Add ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.secondary ,
                                            modifier = Modifier.clickable {
                                                setType =1
                                                showDialogueAddSets = true
                                            }
                                        )

                                    }
                                    Row(
                                        modifier = Modifier.wrapContentSize() ,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            8.dp ,
                                            Alignment.Start
                                        ) ,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Timer ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "${getRestTimeForWorkingUpSets(exercise)} s" ,
                                            color = MaterialTheme.colorScheme.primary ,
                                            modifier = Modifier.clickable {
                                                restTimeType = SetSlot.SetType.WorkingSet.ordinal
                                                showRestTimeDialogue = true

                                            })
                                        Text(
                                                text = "Rest Time between sets" ,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (workingSets.isEmpty()) {
                                        Text(text = "No Working Sets")
                                    } else {
                                        workingSets.onEach {
                                            SetCard(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight() ,
                                                setSlot = it ,
                                                isEditable = currentWeek == lastWeek ,
                                                onSetChanged = onSetChanged ,
                                                isBodyWeight = exercise.isBodyWeight ,
                                                onRemoveSet = { toBeRemoved ->
                                                    onRemoveSet(toBeRemoved)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(showRestTimeDialogue) {
            ChangeRestTimeDialogue(onDismiss = { showRestTimeDialogue = false } ,
                currentInSeconds = if (restTimeType == SetSlot.SetType.WarmUp.ordinal) getRestTimeForWarmUpSets(
                    exercise
                ) else getRestTimeForWorkingUpSets(exercise),
                onSubmit ={value->
                    onEditRestTime(exercise,restTimeType , value)
                })
        }
        if (showDialogueAddSets) {
            RepsAndWeightDialogue(
                title = R.string.add_new_set ,
                caption = null ,
                onDismiss = { showDialogueAddSets = false } ,
                onSubmit = { currentReps , currentWeight ->
                    currentWeek?.let {
                        onAddSet(setType , currentReps , currentWeight , it)
                    }
                    showDialogueAddSets = false
                } ,
                exerciseIsBodyWeight = exercise.isBodyWeight
            )
        }

        if (showDialogue) {
            RepsAndWeightDialogue(onDismiss = { showDialogue = false } ,
                exerciseIsBodyWeight = exercise.isBodyWeight ,
                onSubmit = { reps , weight ->
                    onSubmitStartingPoint(exercise , reps , weight)
                    showDialogue = false
                } , title = R.string.enter_starting_point_message ,
                caption = R.string.no_previous_experience
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun SetCard(
        modifier : Modifier ,
        setSlot : SetSlot ,
        isEditable:Boolean,
        onSetChanged : (SetSlot) -> Unit ,
        isBodyWeight : Boolean ,
        onRemoveSet : (SetSlot) -> Unit

    ) {



        var isInEditMode by remember { mutableStateOf(false) }
        var currentReps by remember { mutableStateOf(setSlot.reps.toString()) }
        var setIsDeleted by remember { mutableStateOf(false)}
        var currentWeight by remember {
            mutableStateOf(
                String.format(
                    "%.1f" ,
                    setSlot.weightInKgs
                )
            )
        }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

       if(!setIsDeleted)
        FlowRow(
            modifier = modifier ,
            verticalArrangement =Arrangement.spacedBy(8.dp ,Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(
                24.dp ,
                Alignment.Start
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f , true) ,
                horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start) ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextBox(
                    modifier = Modifier
                        .width(75.dp)
                        .height(35.dp)
                        .focusRequester(focusRequester) ,
                    suffix = "Reps" ,
                    text = currentReps ,
                    isEditable = isEditable,
                    onValueChanged = {
                        currentReps = it
                        isInEditMode = true
                    } , triggerEditMode = { isInEditMode = true })
                if (!isBodyWeight)
                    Text("X" , color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                if (!isBodyWeight) TextBox(
                    modifier = Modifier
                        .width(75.dp)
                        .height(35.dp)
                        .focusRequester(focusRequester) ,
                    suffix = "Kgs" ,
                    isEditable = isEditable,
                    text = currentWeight ,
                    onValueChanged = {
                        currentWeight = it
                        isInEditMode = true
                    } , triggerEditMode = {
                        isInEditMode = true
                    })
            }

            AnimatedVisibility(visible = isInEditMode) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f , true) ,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentReps = setSlot.reps.toString()
                        currentWeight = String.format(
                            "%.1f" ,
                            setSlot.weightInKgs
                        )
                        isInEditMode = false
                        focusManager.clearFocus()
                    }) {
                        Icon(imageVector = Icons.Filled.Close , contentDescription = null)
                    }
                    IconButton(onClick = {
                        onRemoveSet(setSlot)
                        focusManager.clearFocus()
                        isInEditMode = false
                    }) {
                        Icon(imageVector = Icons.Filled.DeleteForever , contentDescription = null)
                    }
                    IconButton(onClick = {
                        val newReps = try {
                            val newAmount = currentReps.toInt()
                            if (newAmount >= 0) newAmount else null
                        } catch (e : Exception) {
                            null
                        }

                        val weight = try {
                            val newAmount = currentWeight.toDouble()
                            if (newAmount >= 0.000) newAmount else null
                        } catch (e : Exception) {
                            null
                        }

                        if (newReps != null && ((weight != null && !isBodyWeight) || isBodyWeight)) {
                            onSetChanged(
                                setSlot.copy(
                                    reps = newReps ,
                                    weightInKgs = weight ?: setSlot.weightInKgs
                                ).apply { this.uid = setSlot.uid })
                            isInEditMode = false
                            focusManager.clearFocus()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Check , contentDescription = null)
                    }
                }
            }
        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeekSlider(
        modifier : Modifier ,
        weeks : List<Week> ,
        currentWeek : Week? ,
        onWeekClicked : (Week) -> Unit ,

    ) {
        val lastWeek = weeks.lastOrNull { it.index == currentWeek?.index ?: -1 }

        LazyRow(
            modifier = modifier ,
            horizontalArrangement = Arrangement.spacedBy(8.dp) ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(if (weeks.size > 5) weeks.takeLast(5) else weeks , key = { it.uid }) {
                FilterChip(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(35.dp) ,
                    selected = if (currentWeek != null) currentWeek.uid == it.uid else false ,
                    onClick = {
                        onWeekClicked(it)
                    } ,
                    label = {
                        Text(
                            if (currentWeek != null && it.uid == currentWeek.uid) "Current Week" else "Week ${it.index + 1}" ,
                            fontSize = 10.sp ,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } ,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow ,
                        selectedContainerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            item {
               // AssistChip(onClick = { } , label = {
                   Row(
                        modifier = Modifier.height(40.dp) ,
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp ,
                            Alignment.CenterHorizontally
                        ) ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if(currentWeek!=null && lastWeek == currentWeek) Icons.Filled.LockOpen else Icons.Filled.Lock,
                            contentDescription = null ,
                            tint = Color.Yellow ,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text =  if(currentWeek!=null && lastWeek == currentWeek)"Editable" else "Read-Only" , style = TextStyle(fontSize = 10.sp))
                    }
                }
           // }
        }

    }


    @Composable
    fun ExerciseHeadline(
        modifier : Modifier ,
        slot : ExerciseSlot ,
        seeExerciseDocument : (ExerciseSlot) -> Unit ,
        showChart : (ExerciseSlot) -> Unit ,
        replaceExercise : (ExerciseSlot) -> Unit ,
        deleteExercise : (ExerciseSlot) -> Unit
    ) {

        var showActions by remember { mutableStateOf(false) }
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
                    style = Typography.labelMedium ,
                    textAlign = TextAlign.Justify ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.75f)
                )
                Text(
                    text = muscleGroupSt ,
                    style = Typography.labelSmall ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                )
            }
            Box(
                modifier = Modifier.wrapContentHeight() ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert ,
                    contentDescription = null ,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                        .clickable { showActions = true }
                )
                DropdownMenu(expanded = showActions , onDismissRequest = { showActions = false }) {
                    val actions = listOf(
                        Pair("See Exercise Document" , seeExerciseDocument) ,
                        Pair("See Evolution" , showChart) ,
                        Pair(
                            "Replace Exercise" ,
                            replaceExercise
                        ) ,
                        Pair("Remove Exercise" , deleteExercise) ,
                        Pair("Close" ,) { _ : ExerciseSlot -> showActions = false }
                    )
                    actions.onEach {
                        DropdownMenuItem(text = {
                            Text(
                                text = it.first ,
                                style = Typography.bodySmall
                            )
                        } ,
                            onClick = {
                                it.second.invoke(slot)
                                showActions = false
                            })

                    }
                }
            }
        }
    }


    @Composable
    fun TextBox(
        modifier : Modifier ,
        suffix : String ,
        text : String ,
        isEditable:Boolean,
        triggerEditMode:()->Unit,
        onValueChanged : (String) -> Unit ,
    ) {
        var currentText by remember { mutableStateOf(text) }
        val interactionSource = MutableInteractionSource()
        val isClicked =interactionSource.collectIsDraggedAsState()

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
                    enabled = isEditable,
                    value = currentText ,
                    onValueChange = {
                        currentText = it
                        onValueChanged(currentText)
                    } ,
                    interactionSource = interactionSource,
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
        LaunchedEffect(key1 = isClicked.value ){
            if(isClicked.value) triggerEditMode()
        }
    }
}
