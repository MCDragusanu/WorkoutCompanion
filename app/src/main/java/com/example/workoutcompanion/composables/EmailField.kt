package com.example.workoutcompanion.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.theme.*

@Composable
    fun EmailField(
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    text: String,
    state: UIState,
    colors: @Composable (Boolean) -> TextFieldColors = { TextFieldTheme.PrimaryDark(isError = it) }
) {
        var currentText by remember { mutableStateOf(text) }
        var isError = state.isError()

        val playAnimation = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }

        OutlinedTextField(
            value = currentText,
            onValueChange = {
                currentText = it
                onValueChanged(currentText)
            },
            label = {
                Typography.h3.apply {
                    Text(
                        text = "Email",
                        modifier = Modifier,
                        fontSize = fontSize,
                        fontWeight = fontWeight
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null,
                    tint = secondary,
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                AnimatedVisibility(visibleState = playAnimation) {
                    when (state) {
                        UIState.Error -> {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                contentDescription = null,
                                tint = error,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        UIState.Completed -> {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = secondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        else -> {}
                    }
                }
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            colors = colors(isError),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = modifier
        )
    }