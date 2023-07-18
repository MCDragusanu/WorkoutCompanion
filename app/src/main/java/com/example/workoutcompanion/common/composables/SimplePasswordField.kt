package com.example.workoutcompanion.common.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.on_board.composables.FormField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable fun SimplePasswordField(modifier: Modifier,
                              formState: Flow<FormState>,
                              onValueChange:(String)->Unit,
                                    withErrorMessage:Boolean,
){
    FormField(modifier = modifier, withErrorMessage = withErrorMessage, formStateFlow = formState) {flow , _->
        var isVisible by remember { mutableStateOf(false)}
        val currentState by flow.onEach { if(it.state.isLoading()) isVisible = false }.collectAsState(initial = FormState())
        OutlinedTextField(
            modifier = modifier,
            value = currentState.text,
            onValueChange = onValueChange,
            enabled = !currentState.state.isLoading(),
            isError = currentState.state.isError(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if(!isVisible) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = { Icon(imageVector = Icons.Filled.LockOpen, contentDescription = null) },
            trailingIcon = {
                AnimatedVisibility(visible = currentState.state.isCompleted()) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
//                AnimatedVisibility(visible = currentState.state.isError()) {
//                    Icon(
//                        imageVector = Icons.Filled.Warning,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onError
//                    )
//                }
                AnimatedVisibility(visible = currentState.state.isEnabled() || currentState.state.isError()) {
                    Icon(
                        imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            isVisible = !isVisible
                        })
                }
            },
            label = { Text(text = "Password") })
    }
}