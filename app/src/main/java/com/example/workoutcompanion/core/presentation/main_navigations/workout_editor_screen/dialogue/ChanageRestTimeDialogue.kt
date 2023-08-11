package com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen.dialogue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton


@Composable
fun ChangeRestTimeDialogue(onDismiss:()->Unit ,currentInSeconds:Int ,onSubmit:(Int)->Unit) {
    Dialog(onDismissRequest = onDismiss) {
        var current by remember { mutableStateOf(currentInSeconds) }
        Box(
            modifier = Modifier
                .fillMaxWidth().background(MaterialTheme.colorScheme.primaryContainer)
                .wrapContentHeight() , contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Adjust Rest time length")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    horizontalAlignment = Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "$current Seconds")
                    Slider(value = current.toFloat() , onValueChange = {
                        current = it.toInt()
                    } , valueRange = (20.0f..360f))
                }
                AnimatedPrimaryButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) , action = { onSubmit(current)
                    onDismiss()
                    }) { _ , _ ->
                    Text(text = "Confirm Amount")
                }
            }
        }
    }
}