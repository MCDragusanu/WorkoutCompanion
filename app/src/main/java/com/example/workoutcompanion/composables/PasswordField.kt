package com.example.workoutcompanion.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.theme.*

@Composable
    fun PasswordField(
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    txt: String,
    state: UIState,
    colors: @Composable (Boolean) -> TextFieldColors = { TextFieldTheme.PrimaryDark(isError = it) }
): Unit {
    var currentText by remember { mutableStateOf(txt) }
    var showPassword by remember { mutableStateOf(false) }
    val currentState by remember { mutableStateOf(state) }
    OutlinedTextField(
        value = currentText,
        onValueChange = {
            currentText = it
            onValueChanged(currentText)
        },
        label = {
            Typography.h3.apply {
                Text(
                    text = "Password",
                    modifier = Modifier,
                    fontSize = fontSize,
                    fontWeight = fontWeight
                )
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                tint = secondary,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            Icon(
                painter = when (showPassword) {
                       true-> painterResource(id = com.example.workoutcompanion.R.drawable.visibility_48px)
                        false -> painterResource(id = com.example.workoutcompanion.R.drawable.visibility_off_48px)
                    }
                ,
                contentDescription = null,
               /* tint = when (state) {
                    UIState.Completed -> secondary
                    UIState.Enabled -> secondary
                    UIState.Error -> onError
                    else -> {
                        Color.Transparent
                    }
                },*/
                modifier = Modifier
                    .size(30.dp)
                    .clickable { showPassword = !showPassword }
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        ),
        visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = colors(state.isError()),
        isError = state.isError(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier
    )
}


