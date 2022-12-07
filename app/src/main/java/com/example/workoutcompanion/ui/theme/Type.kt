package com.example.workoutcompanion.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontSize = 50.sp,
        fontWeight = FontWeight.ExtraBold,
        color = ColorPalette.primaryText
    ),
    h2 = TextStyle(fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    color = ColorPalette.primaryText
    ),
    h3 = TextStyle(fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = ColorPalette.primaryText
    ),

    h4 = TextStyle(fontWeight = FontWeight.Normal , fontSize = 12.sp, color = ColorPalette.primaryText.copy(alpha = 0.5f)),

    h5 = TextStyle(fontWeight = FontWeight.Normal , fontSize = 10.sp),

    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),

caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

)