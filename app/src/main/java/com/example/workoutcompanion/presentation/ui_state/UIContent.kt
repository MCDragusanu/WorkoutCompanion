package com.example.workoutcompanion.presentation.ui_state

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

class UIContent(uiState: UIState, content:@Composable (brush:Brush, contentColor:Color) ->Unit, backgroundBrush: Brush, contentColor: Color) {
    val uiState: UIState
    val drawContent: @Composable (brush: Brush, contentColor: Color) -> Unit
    val backgroundBrush: Brush
    val contentColor: Color

    init {
        this.uiState = uiState
        this.drawContent = content
        this.backgroundBrush = backgroundBrush
        this.contentColor = contentColor
    }

}