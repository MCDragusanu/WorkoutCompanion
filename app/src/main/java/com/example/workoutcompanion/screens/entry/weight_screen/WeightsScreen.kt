package com.example.workoutcompanion.screens.entry.weight_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Man
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.Weight
import com.example.workoutcompanion.composables.DropDownMenu
import com.example.workoutcompanion.composables.MyTextField
import com.example.workoutcompanion.composables.StepProgressBar
import com.example.workoutcompanion.composables.WeightTextField
import com.example.workoutcompanion.theme.*

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object WeightsScreen {
    @Composable
    operator fun invoke(
        onSubmitWeight: (Weight, Int) -> Unit,
        startWeight: Weight,
        showProgressBar: Boolean = true,
        text: String = "Enter your current Body weight",
        isDarkTheme: Boolean = true
    ) {
        var currentWeight by remember {
            mutableStateOf(startWeight)
        }
        var currentSelection by remember { mutableStateOf(0) }
        var isError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }
        Column(
            modifier = Modifier.background(background),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (showProgressBar) {
                StepProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(32.dp),
                    stepName = "Step 2",
                    progress = 0.44f
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Typography.h2.apply {
                    Text(
                        text = text,
                        Modifier,
                        color = textColor,
                        fontSize,
                        fontStyle,
                        fontWeight,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))

                WeightTextField(
                    onValueChange = {
                        try {
                            val weight = it.toFloat()
                            isError = false
                            errorMessage = ""
                            if (currentSelection == 0) currentWeight.setWeightInKgs(weight)
                            else currentWeight.setWeightInPounds(weight)
                        } catch (e: Exception) {
                            isError = true
                            errorMessage =
                                if (it.isEmpty()) "Please complete this field" else "Enter a valid number"
                        }
                    },
                    onSelectionChanged = {
                        currentSelection = it
                    },
                    startWeight = currentWeight,
                    state = if (isError) UIState.Error else UIState.Enabled)
                AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
                    Typography.caption.apply {
                        Text(
                            text = errorMessage,
                            Modifier.padding(horizontal = 16.dp),
                            color = error,
                            fontSize
                        )
                    }
                }
                DialogCTA(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(16.dp),
                    isError = isError,
                    onClick = {
                            onSubmitWeight(currentWeight, currentSelection)
                    })
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }

    @Composable
    private fun DialogCTA(modifier: Modifier, onClick: () -> Unit, isError: Boolean) {
        var scale = 0.8f
        var isClicked by remember { mutableStateOf(false) }
        val scaleAnim = remember { Animatable(0.8f) }
        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                isClicked = !isClicked
                scope.launch {
                    delay(400)
                    onClick()
                }
            },
            modifier = Modifier.wrapContentSize(),
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            ),
            shape = Shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .background(
                        if (!isError) Brush.verticalGradient(listOf(primary, secondary)) else Brush.verticalGradient(listOf(
                            error, error)),
                        Shapes.medium
                    )
                    .scale(scaleAnim.value)
                    .then(modifier),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Typography.button.apply {
                    Text(
                        text = "Confirm Weight",
                        Modifier,
                        color = if (!isError) onPrimary else Color.Red,
                        fontSize,
                        fontStyle,
                        fontWeight
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                AnimatedVisibility(visible = isClicked && !isError) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        LaunchedEffect(key1 = isClicked) {
            if (!isError) scaleAnim.animateTo(1f, spring(Spring.DampingRatioMediumBouncy))
        }
    }
}

