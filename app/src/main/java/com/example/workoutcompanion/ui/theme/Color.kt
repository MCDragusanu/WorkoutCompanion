package com.example.workoutcompanion.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

object ColorPalette {

    val primaryBackground = Color(0xFF011124)
    val primaryBlue = Color(0xFF33BBFF)
    val primaryGreen = Color(0xFF57D87B)
    val primaryText = Color(0xFFD9D9D9)
    val primarySurface = Color(0xFF071C29)
    val primaryError = Color(0xFFD67E7E)
    val primarySuccess = Color(0xFF83CE77)
    val onPrimaryBlue = Color(0xFF16232B)
    val primaryOnGreen = Color(0xFF16232B)
    val onPrimarySurface = Color(0x80FFFFFF)
    val onPrimaryError = Color(0xFF3D1F1F)
    val onPrimarySuccess = Color(0xFF193D13)
    val onSurfaceDivider = Color(0xFF28353D)
    val primaryEnabled = Color(0xFF3D6F8E)
    val gradientPrimary = Brush.linearGradient(colors = listOf(primaryBlue , primaryGreen))
    val gradientError =  Brush.linearGradient(colors = listOf(primaryError , primaryError))
    val gradientSuccess = Brush.verticalGradient(colors = listOf(primarySuccess , primarySuccess))
}