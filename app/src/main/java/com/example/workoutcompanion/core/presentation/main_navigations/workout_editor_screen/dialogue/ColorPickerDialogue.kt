package com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.compose.workoutTagColorPairs
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton
import com.example.workoutcompanion.ui.Typography


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPicker(title:String, description:String, current :Pair<Color,Color>,onDismiss:()->Unit,options : List<Pair<Color , Color>> = workoutTagColorPairs, onSubmitColor:(Pair<Color , Color>)->Unit ) {
    var pickedColor by remember { mutableStateOf<Pair<Color , Color>?>(null) }
    Log.d("Test" , options.size.toString())
    Dialog(onDismissRequest = { onDismiss() } ,
        properties = DialogProperties(dismissOnBackPress = true , dismissOnClickOutside = true)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() ,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(12.dp) ,
                verticalArrangement = Arrangement.spacedBy(12.dp) ,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title ,
                    style = Typography.headlineLarge ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.75f)
                )
                Text(
                    text = description ,
                    style = Typography.bodySmall ,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                )
                FlowColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() , maxItemsInEachColumn = 4 ,
                    verticalArrangement = Arrangement.SpaceEvenly ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    options.onEach {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .padding(1.dp)
                                .clickable {
                                    pickedColor = it
                                }
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            it.first ,
                                            it.second
                                        ) ,
                                    ) ,
                                    shape = CircleShape
                                ) ,
                            contentAlignment = Alignment.Center
                        ) {
                            pickedColor?.let { picked ->
                                if (picked.first == it.first && picked.second == it.second)
                                    Icon(
                                        imageVector = Icons.Filled.Check ,
                                        contentDescription = null ,
                                        tint = Color.Black.copy(alpha = 0.7f)
                                    )
                            }

                        }
                    }
                }
                AnimatedPrimaryButton(modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(60.dp) , action = {
                    pickedColor?.let {
                        onSubmitColor(it)
                        onDismiss()
                    }
                }) { _ , _ ->
                    Text(text = "Confirm Pick")
                }
            }
        }
    }
}

