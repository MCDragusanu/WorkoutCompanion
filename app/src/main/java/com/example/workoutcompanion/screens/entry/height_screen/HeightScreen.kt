package com.example.workoutcompanion.screens.entry.height_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.common.Height
import com.example.workoutcompanion.composables.HeightTextField
import com.example.workoutcompanion.composables.StepProgressBar
import com.example.workoutcompanion.theme.*

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object HeightScreen {
    @Composable
    operator fun invoke(

        onGetHeight: (Height, Int) -> Unit,
        isDarkTheme: Boolean = true
    ) {
        var isError by remember {
            mutableStateOf(false)
        }
        var currentHeight by remember {
            mutableStateOf(Height(175f))
        }
        var currentSelection by remember {
            mutableStateOf(0)
        }
        Box(modifier = Modifier.background(background)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StepProgressBar(
                    stepName = "Step 3 ", progress = 0.66f, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                    Alignment.CenterHorizontally
                ) {
                    Typography.h2.apply {
                        Text(
                            text = "Enter your height",
                            Modifier,
                            color = textColor,
                            fontSize,
                            fontStyle,
                            fontWeight,
                            textAlign = TextAlign.Center
                        )
                    }
                    HeightTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight().padding(16.dp),
                        onInputValid = { height: Height, i: Int ->
                            currentHeight = height
                            currentSelection = i

                        },
                        onInputInvalid = {
                            isError = true
                        },
                        isDarkTheme = true, startHeight = currentHeight
                    )
                    DialogCTA(
                        modifier = Modifier.fillMaxWidth().height(55.dp).padding(16.dp),
                        onClick = {
                            if (!isError)

                                onGetHeight(currentHeight, currentSelection)
                        },
                        isError = isError
                    )
                }
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
                        Brush.horizontalGradient(listOf(primary, secondary)),
                        Shapes.medium
                    )
                    .scale(scaleAnim.value)
                    .then(modifier),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Typography.button.apply {
                    Text(
                        text = "Confirm Height",
                        Modifier,
                        color = if (!isError) onPrimary else error,
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