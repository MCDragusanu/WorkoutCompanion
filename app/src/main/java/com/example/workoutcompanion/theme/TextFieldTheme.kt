package com.example.workoutcompanion.theme

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object TextFieldTheme {
    @Composable
    fun PrimaryDark(isError:Boolean= false) = TextFieldDefaults.outlinedTextFieldColors(
        textColor = textColor,
        backgroundColor = surface,
        cursorColor = primary,
        unfocusedLabelColor = onBackground,
        unfocusedBorderColor = onSurface,
        focusedBorderColor = onBackground,
        disabledLabelColor = onBackground,
        disabledTextColor = textColor,
        focusedLabelColor = onBackground,
        trailingIconColor = secondary,
        errorLabelColor = error,
        errorCursorColor = error,
        errorBorderColor = error,
        errorLeadingIconColor = error,
        errorTrailingIconColor = error,
        )


}