package com.example.workoutcompanion.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.theme.*

object StepProgressBar {

    @Composable
    operator fun invoke(
        modifier: Modifier,
        stepName: String,
        progress: Float,
        horizondalPadding:Dp = 32.dp
    ) {
        val progressAnimation = remember { Animatable(0f) }

        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Typography.h3.apply {
                    Text(
                        text = stepName,
                        Modifier,
                        color = textColor,
                        fontSize,
                        fontStyle,
                        fontWeight,
                        textAlign = TextAlign.Center
                    )
                }
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = horizondalPadding),
                    color = primary,
                    backgroundColor = background,
                    progress = progressAnimation.value/100f
                )
            }
        }
        LaunchedEffect(key1 = true) {
            progressAnimation.animateTo(progress*100f)
        }
    }
}