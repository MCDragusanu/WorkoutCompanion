package com.example.workoutcompanion.presentation

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.example.workoutcompanion.ui.theme.ColorPalette

object TextFieldTheme {
    @Composable
    fun primary(isError:Boolean) = TextFieldDefaults.outlinedTextFieldColors(
        textColor = ColorPalette.onPrimarySurface,
        backgroundColor = ColorPalette.primaryGreen,
        cursorColor = ColorPalette.primaryBlue,
        unfocusedLabelColor = ColorPalette.primaryBlue,
        unfocusedBorderColor = ColorPalette.primarySurface,
        focusedBorderColor = ColorPalette.primaryBlue,
        focusedLabelColor = ColorPalette.primaryBlue,
        errorLabelColor = ColorPalette.primaryError,
        errorCursorColor = ColorPalette.primaryError,
        errorBorderColor = ColorPalette.primaryError,
        errorLeadingIconColor = ColorPalette.primaryError,
        errorTrailingIconColor = ColorPalette.primaryError,
        )
}