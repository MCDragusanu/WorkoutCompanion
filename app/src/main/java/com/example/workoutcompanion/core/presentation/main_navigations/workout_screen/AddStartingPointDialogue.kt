package com.example.workoutcompanion.core.presentation.main_navigations.screens.training_program_dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton

@Composable
fun AddStartingPointDialogue(onDismiss:()->Unit , onSubmit:(Int , Double)->Unit , exerciseIsBodyWeight:Boolean) {
    var repsString by remember { mutableStateOf("") }
    var weightString by remember { mutableStateOf("") }
    var repsErrorCode by remember { mutableStateOf<Int?>(null) }
    var weightErrorCode by remember { mutableStateOf<Int?>(null) }
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface) , contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp) ,
                verticalArrangement = Arrangement.spacedBy(24.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally ,
            ) {
                Text(text = stringResource(R.string.enter_starting_point_message))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f , true)
                            .height(70.dp) ,
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp ,
                            Alignment.CenterVertically
                        ) ,
                        horizontalAlignment = Alignment.Start
                    ) {
                        OutlinedTextField(
                            value = repsString ,
                            onValueChange = { repsString = it } ,
                            isError = repsErrorCode != null ,
                            suffix = {
                                Text(text = "Reps")
                            } , modifier = Modifier.weight(1f , true)
                        )
                        AnimatedVisibility(visible = repsErrorCode != null) {
                            repsErrorCode?.let {
                                Text(
                                    text = stringResource(id = it) ,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                    if (!exerciseIsBodyWeight) {
                        Column(
                            modifier = Modifier
                                .weight(1f , true)
                                .height(70.dp) ,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp ,
                                Alignment.CenterVertically
                            ) ,
                            horizontalAlignment = Alignment.Start
                        ) {
                            OutlinedTextField(
                                value = weightString ,
                                onValueChange = { weightString = it } ,
                                isError = weightErrorCode != null ,
                                suffix = {
                                    Text(text = "Kgs")
                                } , modifier = Modifier.weight(1f , true))
                            AnimatedVisibility(visible = weightErrorCode!=null) {
                                weightErrorCode?.let {
                                    Text(
                                        text = stringResource(id = it) ,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedPrimaryButton(action = {
                    val repAmount = try {
                        repsErrorCode = null
                        repsString.toInt()
                    } catch (e : Exception) {
                        repsErrorCode = R.string.error_invalid_interger
                        null
                    }
                    val weightAmount = try {
                        weightErrorCode = null
                        val amount = weightString.toDouble()
                        if (amount > 0.0000) amount else null
                    } catch (e : Exception) {
                        weightErrorCode = R.string.error_invalid_decimal
                        null
                    }
                    if (repAmount != null && weightAmount != null) {
                        onSubmit(
                            repAmount ,
                            if (!exerciseIsBodyWeight) weightAmount else -1.0 ,
                        )
                    }
                    onDismiss()
                } , modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(55.dp)) { _ , contentColor ->
                    Text(text = "Save Result" , color = contentColor)
                }
            }
        }
    }
}