package com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton
import com.example.workoutcompanion.ui.Typography

@Composable
fun ChangeWorkoutNameDialogue(onDismiss:()->Unit , text:String , onSubmit:(String)->Unit) {
    var currentText by remember { mutableStateOf(text) }
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = Modifier.wrapContentSize().background(MaterialTheme.colorScheme.secondaryContainer)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp) ,
                horizontalAlignment = Alignment.Start ,
                verticalArrangement = Arrangement.spacedBy(16.dp , Alignment.Top)
            ) {
                Text(text = "Change Workout Name" , style = Typography.labelMedium)
                TextField(
                    value = currentText ,
                    onValueChange = { currentText = it } ,
                    isError = currentText.isBlank())
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    verticalAlignment = Alignment.CenterVertically ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Cancel" ,
                        color = MaterialTheme.colorScheme.onErrorContainer ,
                        style = Typography.labelMedium,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                    AnimatedPrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp) ,
                        action = { if (currentText.isNotBlank()) onSubmit(currentText) }) { _ , _ ->
                        Text(text = "Change Name")
                    }
                }
            }
        }
    }
}