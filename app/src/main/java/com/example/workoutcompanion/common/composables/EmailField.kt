package com.example.workoutcompanion.common.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.on_board.composables.FormField
import kotlinx.coroutines.flow.Flow

@Composable
fun EmailField(modifier: Modifier,
               formState: Flow<FormState>,
               onValueChange:(String)->Unit, ) {
    FormField(modifier = modifier, withErrorMessage = true, formStateFlow = formState) { flow , _modifier->
        val currentState by flow.collectAsState(initial = FormState())
        OutlinedTextField(
            modifier = modifier,
            value = currentState.text,
            onValueChange = onValueChange,
            enabled = !currentState.state.isLoading(),
            isError = currentState.state.isError(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
            trailingIcon = {
                AnimatedVisibility(visible = currentState.state.isCompleted()) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                AnimatedVisibility(visible = currentState.state.isError()) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            },
            label = { Text(text = "Email") })
    }
}