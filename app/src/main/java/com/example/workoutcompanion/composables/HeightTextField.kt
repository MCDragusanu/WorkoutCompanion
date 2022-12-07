package com.example.workoutcompanion.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.Height
import com.example.workoutcompanion.common.UIState

import com.example.workoutcompanion.theme.TextFieldTheme
import com.example.workoutcompanion.theme.Typography

object HeightTextField {
    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    operator fun invoke(
        modifier: Modifier,
        startHeight: Height,
        onInputValid: (Height, Int) -> Unit,
        onInputInvalid: () -> Unit,
        isDarkTheme: Boolean = true,
        withErrors: Boolean = true
    ) {

        val currentHeight by remember { mutableStateOf(startHeight) }
        var isError by remember { mutableStateOf(false) }
        var errorMesssage by remember { mutableStateOf("") }
        var currentSelection by remember { mutableStateOf(0) }

        val choices = listOf("Metric", "Imperial")
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AnimatedContent(targetState = currentSelection ,modifier = Modifier.fillMaxWidth()) { targetState ->
                when (targetState) {
                    0 -> MetricContent(
                        choices = choices,
                        height = currentHeight,
                        error = isError,
                        onInputValid = { height, index ->
                            isError = false
                            errorMesssage = ""
                            currentHeight.setHeightInCms(height.toCms())
                            onInputValid(currentHeight, index)
                        },
                        onChangeContent = {

                            errorMesssage = ""
                            isError = false
                            currentSelection = it
                        },
                        onInputInvalid = {
                            isError = true
                            errorMesssage = it
                        }
                    )
                    1 -> ImperialContent(
                        choices = choices,
                        height = currentHeight,
                        error = isError,
                        onInputValid = { height, index ->
                            isError = false
                            errorMesssage = ""
                            currentHeight.setHeightInCms(height.toCms())
                            onInputValid(currentHeight, index)
                        },
                        onChangeContent = {
                            errorMesssage = ""
                            isError = false
                            currentSelection = it
                        },
                        onInputInvalid = {
                            isError = true
                            errorMesssage = it
                        }
                    )
                }
            }
            if (withErrors) {
                AnimatedVisibility(visible = isError) {
                    Typography.caption.apply {
                        Text(
                            text = errorMesssage,
                            Modifier,
                            color = Color.Red,
                            fontSize,
                            fontStyle,
                            fontWeight
                        )
                    }
                }
            }
        }
    }

    private @Composable
    fun MetricContent(
        choices: List<String>,
        height: Height,
        error: Boolean,
        onInputValid: (Height, Int) -> Unit,
        onChangeContent: (Int) -> Unit, onInputInvalid: (String) -> Unit
    ) {

        var currentHeight by remember { mutableStateOf(height) }
        var isError by remember { mutableStateOf(error) }
        var currentTextCms by remember {
            mutableStateOf("")
        }
        var errorMesssage by remember {
            mutableStateOf("")
        }
        var show by remember {
            mutableStateOf(false)
        }
        var currentSelection by remember {
            mutableStateOf(0)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MyTextField(
                modifier = Modifier
                    .weight(1f, true)
                    .wrapContentHeight(),
                onValueChanged = {
                    try {
                        currentTextCms = it
                        isError = false
                        errorMesssage = ""
                        val height = it.toFloat()
                        currentHeight.setHeightInCms(height)
                        onInputValid(currentHeight, 0)
                    } catch (e: Exception) {

                        isError = true
                        errorMesssage =
                            if (it.isEmpty()) "Please complete this field" else "Please enter a valid number"
                        onInputInvalid(errorMesssage)
                    }
                },
                text = currentTextCms,
                label = { Text(text = "Height", fontSize = 12.sp) },
                leadingIcon = {},
                placeholder = {},
                trailingIcon = {},

                keyboardType = KeyboardType.Number,
                isError = isError
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(modifier = Modifier
                .clickable { show = !show }
                .weight(1f, true)
                .wrapContentHeight()) {
                DropDownMenu(
                    parent = {
                        MyTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(),
                            text = choices[currentSelection],
                            onValueChanged = {},
                            label = {
                                Typography.h3.apply {
                                    Text(
                                        text = "Unit",
                                        Modifier,
                                        maxLines = 1,
                                        fontSize = 8.sp,
                                        fontWeight = fontWeight
                                    )
                                }
                            },
                            isEnabled = false,
                            trailingIcon = {},
                            placeholder = { },
                            leadingIcon = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = 12.sp),

                        )
                    },
                    getItemSelected = {
                        show = false
                        currentSelection = it
                        onChangeContent(currentSelection)

                    },
                    choices = choices,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    expanded = show
                )
            }
        }
    }

    private @Composable
    fun ImperialContent(
        choices: List<String>,
        height: Height,
        error: Boolean,
        onInputValid: (Height, Int) -> Unit,
        onChangeContent: (Int) -> Unit, onInputInvalid: (String) -> Unit
    ) {
        var show by remember {
            mutableStateOf(false)
        }
        var nFeet by remember {
            mutableStateOf(height.toFeetAndInches().first)
        }
        var currentSelection by remember {
            mutableStateOf(1)
        }
        var nInches by remember {
            mutableStateOf(height.toFeetAndInches().second)
        }
        var errorMesssage by remember {
            mutableStateOf("")
        }
        var currentHeight = height
        var currentTextInches by remember {
            mutableStateOf("$nFeet")
        }
        var currentTextFeet by remember {
            mutableStateOf("$nFeet")
        }
        var isError by remember {
            mutableStateOf(error)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start)
            ) {
                MyTextField(
                    modifier = Modifier
                        .weight(1f, true)
                        .wrapContentHeight(),
                    onValueChanged = {
                        try {
                            currentTextFeet = it
                            isError = false
                            errorMesssage = ""
                            nFeet = it.toFloat().toInt()
                            currentHeight.setHeightInFeetInches(nFeet, nInches)
                            onInputValid(currentHeight, currentSelection)
                        } catch (e: Exception) {

                            isError = true
                            errorMesssage =
                                if (it.isEmpty()) "Please complete this field" else "Please enter a valid number"
                            onInputInvalid(errorMesssage)
                        }
                    },
                    text = currentTextFeet,
                    label = { Text(text = "Feet") },
                    leadingIcon = {},
                    placeholder = {},
                    trailingIcon = {},
                    keyboardType = KeyboardType.Number,
                    isError = isError
                )
                MyTextField(
                    modifier = Modifier
                        .weight(1f, true)
                        .wrapContentHeight(),
                    onValueChanged = {
                        try {
                            currentTextInches = it
                            isError = false
                            errorMesssage = ""
                            nInches = it.toFloat().toInt()
                            currentHeight.setHeightInFeetInches(nFeet, nInches)
                            onInputValid(currentHeight, currentSelection)
                        } catch (e: Exception) {

                            isError = true
                            errorMesssage =
                                if (it.isEmpty()) "Please complete this field" else "Please enter a valid number"
                            onInputInvalid(errorMesssage)
                        }
                    },
                    text = currentTextInches,
                    label = { Text(text = "Inches") },
                    leadingIcon = {},
                    placeholder = {},
                    trailingIcon = {},

                    keyboardType = KeyboardType.Number,
                    isError = isError
                )
            }
            Box(modifier = Modifier
                .clickable { show = !show }
                .wrapContentWidth()
                .wrapContentHeight()) {
                DropDownMenu(
                    parent = {
                        MyTextField(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            text = choices[currentSelection],
                            onValueChanged = {},
                            label = {
                                Typography.h3.apply {
                                    Text(
                                        text = "Unit",
                                        Modifier,
                                        maxLines = 1,
                                        fontSize = 8.sp,
                                        fontWeight = fontWeight
                                    )
                                }
                            },
                            isEnabled = false,
                            trailingIcon = {},
                            placeholder = { },
                            leadingIcon = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = 12.sp),

                        )
                    },
                    getItemSelected = {
                        show = false
                        currentSelection = it
                        onChangeContent(currentSelection)

                    },
                    choices = choices,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    expanded = show
                )
            }
        }
    }
}
