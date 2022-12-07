package com.example.workoutcompanion.composables

import androidx.compose.animation.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.workoutcompanion.common.UIContent
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.theme.Shapes
import kotlinx.coroutines.flow.*

object CTAButton {

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
   operator fun invoke(
        modifier: Modifier,
        onClick:()->Unit,
        content: List<UIContent>,
        startState: UIState,
        stateFlow: Flow<UIState>,
    ) {

        var currentState = stateFlow.collectAsState(initial = startState)
        Button(onClick = onClick, shape = Shapes.medium, colors = ButtonDefaults.buttonColors(
            Color.Transparent
        ), elevation = null) {
            AnimatedContent(targetState = currentState , transitionSpec = {
                fadeIn() with fadeOut()
            }) { state->

                val currentContent = content.find { it.uiState == state.value}!!
                currentContent.drawContent(
                    currentContent.backgroundBrush,
                    currentContent.contentColor
                )
            }
        }

    }
}