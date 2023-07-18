package com.example.workoutcompanion.ui


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default ,
        fontWeight = FontWeight.Normal ,
        fontSize = 16.sp
    ) ,
    titleLarge = TextStyle(
        fontFamily = FontFamily.Monospace ,
        fontWeight = FontWeight.Bold ,
        fontSize = 42.sp
    ) ,
    titleMedium = TextStyle(
        fontFamily = FontFamily.Monospace ,
        fontWeight = FontWeight.Bold ,
        fontSize = 36.sp
    ) ,
    titleSmall = TextStyle(
        fontFamily = FontFamily.Monospace ,
        fontWeight = FontWeight.Bold ,
        fontSize = 32.sp
    ) ,
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Monospace ,
        fontWeight = FontWeight.Medium ,
        fontSize = 32.sp
    ) ,
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Monospace ,
        fontWeight = FontWeight.Medium ,
        fontSize = 28.sp
    ) ,
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Monospace ,
        fontWeight = FontWeight.Medium ,
        fontSize = 24.sp
    ) ,

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
        )
