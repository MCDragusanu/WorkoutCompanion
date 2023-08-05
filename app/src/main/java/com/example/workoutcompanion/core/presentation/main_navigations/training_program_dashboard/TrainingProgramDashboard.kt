package com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton
import com.example.workoutcompanion.core.data.workout.workout.WorkoutMetadata
import com.example.workoutcompanion.core.domain.model.progression_overload.ExerciseProgressionSchema
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import com.example.workoutcompanion.workout_designer.progression_overload.TrainingParameters

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
        val parameters by viewModel.trainingParameters.collectAsState()
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
                item {
                    TrainingParametersEditor(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() , parameters , addDefaultSchemas = {
                                viewModel.generateInitialParameters()
                        } , onUpdateSchema = {appliedTo , schema->
                            Log.d("Test" , "update triggered")
                            viewModel.updateSchema(appliedTo , schema)
                        })
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

    private @Composable
    fun TrainingParametersEditor(modifier : Modifier , parameters : TrainingParameters?,addDefaultSchemas:()->Unit , onUpdateSchema : (Int , ExerciseProgressionSchema) -> Unit) {
        var currentChoice by remember { mutableStateOf(0) }

        if (parameters == null) {
            Card(modifier = modifier) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(12.dp) ,
                    verticalArrangement = Arrangement.spacedBy(12.dp , Alignment.Top) ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "There are no schemas found")
                    Text(
                        text = "Click to add the schemas" ,
                        color = MaterialTheme.colorScheme.secondary ,
                        modifier = Modifier.clickable { addDefaultSchemas() })
                }
            }
        } else {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text("Training Parameters" , style = Typography.headlineSmall)
                Spacer(modifier = Modifier.size(24.dp))
                Card(
                    modifier = modifier ,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(12.dp) ,
                        verticalArrangement = Arrangement.spacedBy(12.dp , Alignment.Top) ,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight() ,
                            horizontalArrangement = Arrangement.SpaceBetween ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f , true)
                                    .height(55.dp)
                                    .background(
                                        animateColorAsState(if (currentChoice == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer).value ,
                                        shape = cardShapes.small
                                    )
                                    .clickable {
                                        currentChoice = 0
                                    } , contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Primary Compounds" , fontSize = 10.sp)
                            }
                            Box(modifier = Modifier
                                .weight(1f , true)
                                .height(55.dp)
                                .background(
                                    animateColorAsState(if (currentChoice == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer).value ,
                                    shape = cardShapes.small
                                )
                                .clickable {
                                    currentChoice = 1
                                } , contentAlignment = Alignment.Center) {
                                Text(text = "Secondary Compounds" , fontSize = 10.sp)
                            }
                            Box(modifier = Modifier
                                .weight(1f , true)
                                .height(55.dp)
                                .background(
                                    animateColorAsState(if (currentChoice == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer).value ,
                                    shape = cardShapes.small ,

                                    )
                                .clickable {
                                    currentChoice = 2
                                } , contentAlignment = Alignment.Center) {
                                Text(text = "Isolation" , fontSize = 10.sp)
                            }
                        }
                        AnimatedContent(targetState = currentChoice) {
                            SchemaCard(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight() , schema =when(it){
                                0-> parameters.primaryCompoundSchema
                                1->parameters.secondaryCompoundSchema
                                else -> parameters.isolationSchema
                            } , onUpdateSchema = { appliedTo , newSchema->
                                onUpdateSchema(appliedTo , newSchema)
                            })
                        }
                    }
                }
            }
        }
    }
    
    @Composable
    fun SchemaCard(modifier : Modifier,schema : ExerciseProgressionSchema , onUpdateSchema:(Int , ExerciseProgressionSchema)->Unit) {
        var currentRepRange by remember { mutableStateOf(schema.repRange.first.toFloat()..schema.repRange.last.toFloat()) }
        var currentWeightGrowthRate by remember { mutableStateOf(0) }
        Column(
            modifier = modifier ,
            horizontalAlignment = Alignment.Start ,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                verticalArrangement = Arrangement.spacedBy(8.dp) ,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    horizontalArrangement = Arrangement.spacedBy(16.dp) ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Active Rep Range" , style = Typography.labelLarge)
                    Text(
                        "${currentRepRange.start.toInt()} - ${currentRepRange.endInclusive.toInt()} reps" ,
                        color = MaterialTheme.colorScheme.primary ,
                        style = Typography.labelMedium
                    )
                }
                Text(
                    stringResource(R.string.rep_range_body) ,
                    fontSize = 10.sp ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.25f) ,
                )

                RangeSlider(
                    value = currentRepRange ,
                    valueRange = (1.0f..20.0f) ,
                    onValueChange = {
                        currentRepRange = it
                        onUpdateSchema(
                            schema.appliedTo.ordinal ,
                            schema.copy(repRange = (it.start.toInt()..it.endInclusive.toInt())).apply {
                                this.uid = schema.uid
                            }
                        )
                    } ,
                    steps = 25
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                verticalArrangement = Arrangement.spacedBy(8.dp) ,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Weight Growth Modifier" , style = Typography.labelLarge)
                Text(stringResource(R.string.weight_grouth_modifier_body) , fontSize = 10.sp , color = MaterialTheme.colorScheme.onBackground.copy(0.25f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f , true)
                            .height(50.dp).clickable {
                                onUpdateSchema(schema.appliedTo.ordinal , schema.copy(weightIncrementCoeff = ExerciseProgressionSchema.SMALL_GROWTH_COEFF).apply {
                                    this.uid = schema.uid
                                })
                                currentWeightGrowthRate = 0
                            }
                            .background(
                                animateColorAsState(if (currentWeightGrowthRate == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer).value ,
                                shape = cardShapes.small
                            ) ,
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp) ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Small" , style = Typography.labelMedium)
                            Text(
                                fontSize = 10.sp,
                                text = "1.5 % increase" ,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f , true)
                            .height(60.dp).clickable {
                                currentWeightGrowthRate = 1
                                onUpdateSchema(schema.appliedTo.ordinal , schema.copy(weightIncrementCoeff = ExerciseProgressionSchema.NORMAL_GROWTH_COEFF).apply {
                                    this.uid = schema.uid
                                })
                            }
                            .background(
                                animateColorAsState(if (currentWeightGrowthRate == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer).value ,
                                shape = cardShapes.small
                            ) ,
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.padding(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp , Alignment.CenterVertically) ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Default" , style = Typography.labelMedium)
                            Text(
                                text = "3.75 % increase" ,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f , true)
                            .height(60.dp).clickable {
                                onUpdateSchema(schema.appliedTo.ordinal , schema.copy(weightIncrementCoeff = ExerciseProgressionSchema.BIG_GROWTH_COEFF).apply {
                                    this.uid = schema.uid
                                })
                                currentWeightGrowthRate = 2
                            }
                            .background(
                                animateColorAsState(if (currentWeightGrowthRate == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer).value ,
                                shape = cardShapes.small
                            ) ,
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Big" , style = Typography.labelMedium)
                            Text(
                                text = "5 % increase" ,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f)
                            )
                        }
                    }
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
