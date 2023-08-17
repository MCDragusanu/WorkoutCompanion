package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.graphics.text.MeasuredText
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workoutcompanion.R
import com.example.workoutcompanion.core.data.workout.exercise_slot.ExerciseSlot
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes


@Composable
fun SessionReportSnackbar(onDismiss:()->Unit , results:List<ExerciseReport> , onRegress:(ExerciseSlot)->Unit , onStatusQuo:(ExerciseSlot)->Unit , onProgress:(ExerciseSlot)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() ,
        horizontalAlignment = Alignment.Start ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.arm_flex) ,
                contentDescription = null ,
                modifier = Modifier.size(48.dp)
            )
            Text(text = "Congratulations!" , style = Typography.headlineMedium)
            Text(
                text = "See your result and increase those weights!" ,
                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            results.onEach { result ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentHeight() ,
                        horizontalAlignment = Alignment.Start ,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val percent =
                            ((result.setsCompleted) / (result.setsFailed + result.setsCompleted).toFloat())
                        Column(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight() ,
                                horizontalArrangement = Arrangement.SpaceBetween ,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = result.exerciseSlot.exerciseName ,
                                    style = Typography.labelSmall ,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                HalfCircularProgressBar(
                                    modifier = Modifier
                                        .height(25.dp)
                                        .width(25.dp) ,
                                    percent = percent
                                ) {
                                    Column(
                                        modifier = Modifier.wrapContentSize().background(Color.Transparent) ,
                                        verticalArrangement = Arrangement.spacedBy(
                                            4.dp ,
                                            Alignment.CenterVertically
                                        ) ,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = String.format("%.1f" , percent * 100) + "%" ,
                                            fontSize = 12.sp ,
                                            fontWeight = FontWeight.Bold ,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(text = "Completion Rate")
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight() ,
                                verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SurfaceButton(
                                    modifier = Modifier
                                        .weight(1f , true).padding(4.dp)
                                        .wrapContentHeight() ,
                                    onClick = { onRegress(result.exerciseSlot) }) {
                                    Column(
                                        modifier = Modifier.wrapContentSize() ,
                                        horizontalAlignment = Alignment.CenterHorizontally ,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.StackedLineChart ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                        Text("Decrease Difficulty",color = MaterialTheme.colorScheme.onBackground)
                                        if (percent *100< 50f) {
                                            Text(text = "Recommended" , color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                                SurfaceButton(
                                    modifier = Modifier
                                        .weight(1f , true).padding(4.dp)
                                        .wrapContentHeight() ,
                                    onClick = { onStatusQuo(result.exerciseSlot) }) {
                                    Column(
                                        modifier = Modifier.wrapContentSize() ,
                                        horizontalAlignment = Alignment.CenterHorizontally ,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.StackedLineChart ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                        Text("Status Quo" , color = MaterialTheme.colorScheme.onBackground)
                                        if (percent*100 in 50.0f..90.0f) {
                                            Text(text = "Recommended",color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                                SurfaceButton(
                                    modifier = Modifier
                                        .weight(1f , true).padding(4.dp)
                                        .wrapContentHeight() ,
                                    onClick = { onProgress(result.exerciseSlot) }) {
                                    Column(
                                        modifier = Modifier.wrapContentSize() ,
                                        horizontalAlignment = Alignment.CenterHorizontally ,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.StackedLineChart ,
                                            contentDescription = null ,
                                            tint = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                        Text("Increase Difficulty",color = MaterialTheme.colorScheme.onBackground)
                                        if (percent*100 > 90.0f) {
                                            Text(text = "Recommended",color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun SurfaceButton( modifier : Modifier, onClick:()->Unit , content:@Composable()()->Unit) {
    Surface(
        modifier = modifier ,
        shape = cardShapes.medium ,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Box(modifier = Modifier.wrapContentSize() , contentAlignment = Alignment.Center){
            content()
        }
    }
}
@Composable
private fun HalfCircularProgressBar(modifier : Modifier ,percent:Float , label :@Composable()()->Unit) {
    val progressAnimation = remember { Animatable(0f) }
    Column(modifier = Modifier.wrapContentSize() , verticalArrangement = Arrangement.SpaceEvenly , horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            progress = percent ,
            modifier = modifier ,
            color = MaterialTheme.colorScheme.primary ,
            trackColor = MaterialTheme.colorScheme.secondaryContainer
        )
        Surface(
            modifier =
            Modifier.fillMaxWidth().wrapContentHeight() ,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            label()
        }
    }
    LaunchedEffect(key1 = percent) {
        progressAnimation.animateTo(percent)
    }
}
