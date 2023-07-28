package com.example.workoutcompanion.common.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.example.compose.getPalette
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimatedStatefulCallToAction(ctaState: Flow<UIState> ,
                                 modifier : Modifier ,
                                 action:()->Unit ,
                                 enabled:Boolean = true ,
                                 content:@Composable()(UIState , containerColor: Color , contentColor:Color)->Unit ,

                                 ) {
    val interactionSource = MutableInteractionSource()
    val currentState by ctaState.collectAsState(initial = UIState.Enabled)
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.0f else 0.95f ,
        finishedListener = { action() })
    val containerColor by animateColorAsState(
        targetValue = when (currentState) {
            UIState.Enabled -> getPalette().current.primary
            UIState.Error -> MaterialTheme.colorScheme.errorContainer
            UIState.Completed -> getPalette().current.primary
            UIState.Loading -> getPalette().current.primary
            UIState.Disbled -> MaterialTheme.colorScheme.surface
        }
    )
    val contentColor by animateColorAsState(
        targetValue = when (currentState) {
            UIState.Enabled -> getPalette().current.onPrimary
            UIState.Error -> MaterialTheme.colorScheme.onErrorContainer
            UIState.Completed -> getPalette().current.onPrimary
            UIState.Loading -> getPalette().current.onPrimary
            UIState.Disbled -> MaterialTheme.colorScheme.onSurface
        }
    )
    Button(
        onClick = {} ,
        interactionSource = interactionSource ,
        enabled = enabled,
        modifier = modifier.then(
            Modifier
                .scale(scale)
                .wrapContentSize(Alignment.Center)
        ) ,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ,
            contentColor = contentColor
        )
    ) {
        AnimatedContent(targetState = currentState , contentAlignment = Alignment.Center) {
            content(it , containerColor , contentColor)
        }
    }
}
@Composable
fun AnimatedPrimaryButton(modifier : Modifier ,
                          action:()->Unit ,
                          content:@Composable()(containerColor: Color , contentColor:Color)->Unit ,
                                 ) {
    val interactionSource = MutableInteractionSource()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.0f else 0.95f ,
        finishedListener = { action() })
    val containerColor by animateColorAsState(
        targetValue = when (isPressed) {
            true -> getPalette().current.primary
            false -> MaterialTheme.colorScheme.surfaceContainer
        }
    )
    val contentColor by animateColorAsState(
        targetValue = when (isPressed) {
            true -> getPalette().current.onPrimary
            false -> MaterialTheme.colorScheme.onSurface
        }
    )
    Button(
        onClick = {} ,
        interactionSource = interactionSource ,
        modifier = modifier.then(
            Modifier
                .scale(scale)
                .wrapContentSize(Alignment.Center)
        ) ,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ,
            contentColor = contentColor
        )
    ) {
         content(containerColor , contentColor)
    }
}